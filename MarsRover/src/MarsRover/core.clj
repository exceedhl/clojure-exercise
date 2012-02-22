(ns MarsRover.core)

(defrecord Plateau [x y marks])
(defrecord Rover [x y direction dead?])

(def *plateau* ^{:private true} (ref nil))

(def *marks* ^{:private true} (ref #{}))

(def action-matrix
  ^{:private true}
  {:N {:left :W :right :E :dx 0 :dy 1}
   :W {:left :S :right :N :dx -1 :dy 0}
   :S {:left :E :right :W :dx 0 :dy -1}
   :E {:left :N :right :S :dx 1 :dy 0}})

(defn init-plateau [x y]
  (dosync (ref-set *plateau* (Plateau. x y []))))

(defn left [rover]
  (assoc rover :direction (:left ((:direction rover) action-matrix))))

(defn right [rover]
  (assoc rover :direction (:right ((:direction rover) action-matrix))))

(defn move [rover]
  (if (contains? @*marks* (dissoc rover :dead?))
    rover
    (assoc rover
    :x (+ (:dx ((:direction rover) action-matrix)) (:x rover))
    :y (+ (:dy ((:direction rover) action-matrix)) (:y rover)))))

(defn- isFallenOut? [rover]
  (or (> (:x rover) (:x @*plateau*)) (> (:y rover) (:y @*plateau*))))

(defn- parse-commands [commands]
  (map #(case %
          \L left
          \R right
          \M move
          :unknown-command) commands))

(defn execute [rover commands]
  (loop [r rover
         cl (parse-commands commands)]
    (cond (empty? cl) r
          (isFallenOut?  ((first cl) r))
          (do
            (dosync (alter *marks* conj (dissoc r :dead?)))
            (assoc r :dead? true))
          true (recur ((first cl) r) (rest cl)))))

