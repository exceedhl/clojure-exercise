(ns MarsRover.test.core
  (:use [MarsRover.core])
  (:use [midje.sweet])
  (:import (MarsRover.core Rover)))

(init-plateau 5 5)

(let [rover (Rover. 1 2 :N false)
      rover2 (Rover. 3 3 :E false)
      rover3 (Rover. 4 1 :S false)]
  (facts "basic operations about rover"
         "turn left"
         (left rover) => (Rover. 1 2 :W false)
         (left (left rover)) => (Rover. 1 2 :S false)
         (left (left (left rover))) => (Rover. 1 2 :E false)
         (left (left (left (left rover)))) => rover
         "turn right"
         (right rover) => (Rover. 1 2 :E false)
         (right (right rover)) => (Rover. 1 2 :S false)
         (right (right (right rover))) => (Rover. 1 2 :W false)
         (right (right (right (right rover)))) => rover
         "move around"
         (move rover) => (Rover. 1 3 :N false))

  (facts "executing commands"
         (execute rover "LMLMLMLMM") => (Rover. 1 3 :N false)
         (execute rover2 "MMRMMRMRRM") => (Rover. 5 1 :E false)
         (execute rover2 "MMRMMLMRRM") => (Rover. 5 1 :E true)
         (execute rover3 "MLMLMRMRM") => (Rover. 5 0 :S false)))

