(ns MarsRover.core)

(defrecord Rover [x y direction])

(def action-matrix
  ^{:private true}
  {:N {:left :W :right :E :dx 0 :dy 1}
   :W {:left :S :right :N :dx -1 :dy 0}
   :S {:left :E :right :W :dx 0 :dy -1}
   :E {:left :N :right :S :dx 1 :dy 0}})

(defn left [rover]
  (assoc rover :direction (:left ((:direction rover) action-matrix))))

(defn right [rover]
  (assoc rover :direction (:right ((:direction rover) action-matrix))))

(defn move [rover]
  (assoc rover
    :x (+ (:dx ((:direction rover) action-matrix)) (:x rover))
    :y (+ (:dy ((:direction rover) action-matrix)) (:y rover))))

(defn- parse-commands [commands]
  (map #(case %
          \L left
          \R right
          \M move
          :unknown-command) commands))

(defn execute [rover commands]
  (loop [r rover
         cl (parse-commands commands)]
    (if (empty? cl)
      r
      (recur ((first cl) r) (rest cl)))))

