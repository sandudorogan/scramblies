(ns scramblies.main.utils
  (:import java.util.Scanner
           java.io.InputStreamReader
           java.io.BufferedReader
           java.util.stream.Collectors))

(defn input-stream->string [stream]
  (let [stream-reader   (InputStreamReader. stream)
        buffered-reader (BufferedReader. stream-reader)]
    (prn stream stream-reader buffered-reader)
    (-> buffered-reader .lines (.collect (. Collectors joining "\n")))))
