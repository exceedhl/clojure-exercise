(ns MarsRover.test.core
  (:use [MarsRover.core])
  (:use [midje.sweet]))

(let [rover (Rover. 1 2 :N)]
  (facts "basic operations about rover"
         "turn left"
         (left rover) => (Rover. 1 2 :W)
         (left (left rover)) => (Rover. 1 2 :S)
         (left (left (left rover))) => (Rover. 1 2 :E)
         (left (left (left (left rover)))) => rover
         "turn right"
         (right rover) => (Rover. 1 2 :E)
         (right (right rover)) => (Rover. 1 2 :S)
         (right (right (right rover))) => (Rover. 1 2 :W)
         (right (right (right (right rover)))) => rover
         "move around"
         (move rover) => {:x 1 :y 3 :direction :N})

  (facts "executing commands"
         (execute rover "LMLMLMLMM") => (Rover. 1 3 :N)
         (execute (Rover. 3 3 :E) "MMRMMRMRRM") => (Rover. 5 1 :E)))

