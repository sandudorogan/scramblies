(ns scramblies.main.scramble)

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
  (let [str1-alphabet (alphabet a)
        str2-alphabet (alphabet b)]
    (->> str2-alphabet
         (map (fn [[letter usage]]
                (>= (get str1-alphabet letter 0) usage)))
         (every? true?))))
