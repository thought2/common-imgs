(ns thought2.sub-images.utils)


(defn lcm-finder [a b]
  "rounds 'b' in order to fullfill lcm(a,b) ~ a"
  (/ a (round (/ a b))))

(defn range-rounded-step
  ([end step]
   (range-rounded-step 0 end step))
  ([start end step]
   (range start end (lcm-finder (- end start) step))))
