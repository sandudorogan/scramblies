(ns scramblies.main.utils.scramble)

(defn alphabet
  "Returns a map with a given string' characters as keys
  and how many times a character is used as vals."
  [str]
  (reduce (fn [map char]
            (update map char #(inc (or % 0))))
          {} str))

(defn scramble?
  "Returns true if a portion of str1 characters
  can be rearranged to match str2, otherwise returns false."
  [a b]
  ;; TODO: compute the alphabets in different threads
  (let [str1-alphabet (alphabet a)
        str2-alphabet (alphabet b)]
    (->> (map (fn [[letter usage]]
                (>= (get str1-alphabet letter 0) usage))
              str2-alphabet)
         (every? true?))))
