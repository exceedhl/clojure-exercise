(ns MarsRover.test.core
  (:use [MarsRover.core])
  (:use [midje.sweet]))

(let [rover {:x 1 :y 2 :direction :N}]
  (facts "basic operations about rover"
         "turn left"
         (left rover) => {:x 1 :y 2 :direction :W}
         (left (left rover)) => {:x 1 :y 2 :direction :S}
         (left (left (left rover))) => {:x 1 :y 2 :direction :E}
         (left (left (left (left rover)))) => rover
         "turn right"
         (right rover) => {:x 1 :y 2 :direction :E}
         (right (right rover)) => {:x 1 :y 2 :direction :S}
         (right (right (right rover))) => {:x 1 :y 2 :direction :W}
         (right (right (right (right rover)))) => rover
         "move around"
         (move rover) => {:x 1 :y 3 :direction :N})

  (facts "executing commands"
         (execute rover "LMLMLMLMM") => {:x 1 :y 3 :direction :N}
         (execute {:x 3 :y 3 :direction :E} "MMRMMRMRRM") => {:x 5 :y 1 :direction :E}))

