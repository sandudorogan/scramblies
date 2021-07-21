(ns scramblies.main.scramble-test
  (:require [scramblies.main.utils.scramble :as sut]
            [clojure.test :as t]))


(t/deftest alphabet-test
  (t/testing "Simple usage"
    (t/is (sut/alphabet "sanduu") {\s 1
                                   \a 1
                                   \n 1
                                   \d 1
                                   \u 2})
    (t/is (sut/alphabet "aaaa") {\a 4}))
  (t/testing "Null data"
    (t/is (sut/alphabet nil) nil))
  (t/testing "Long list"
    (t/is (sut/alphabet (take 100000 (cycle "abs"))))))

(t/deftest scramble?-test
  (t/testing "Simple usage"
    (t/is (sut/scramble? "sandu" "sandu"))
    (t/is (sut/scramble? "rekqodlw" "world"))
    (t/is (sut/scramble? "cedewaraaossoqqyt" "codewars"))
    (t/is (not (sut/scramble? "katas"  "steak"))))
  (t/testing "Null data"
    (t/is (sut/scramble? "sandu" nil))
    (t/is (not (sut/scramble? nil "sandu")))
    (t/is (sut/scramble? nil nil)))
  (t/testing "Long lists"
    (t/is (sut/scramble? (take 100000 (cycle "abs")) (take 100000 (cycle "abs"))))
    (t/is (sut/scramble? (take 100001 (cycle "abs")) (take 100000 (cycle "abs"))))
    (t/is (not (sut/scramble? (take 100000 (cycle "abs")) (take 100001 (cycle "abs")))))))
