(ns Clojure-webapp.test.model
  (:use clojure.test)
  (:use Clojure-webapp.model)
  (:require [Clojure-webapp.test.testdata :as td]))

(deftest checkwincombos
  (doseq [board# td/win-combinations]
    (is (winner-in-rows-cols-ordiags? board#))))

(deftest nodoublenumbersoncard
    (is (= 25 (count (set (flatten (randboard)))))))

(deftest dodoublesgetrokken
   (is (= 99 (count (set (random-select 99 (range 1 100)))))))

(deftest playerlose
  (is (full-board? 98)))
(deftest boardnotfull
  (is (not (full-board? 97))))


(run-tests)