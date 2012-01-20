; Full Credit to Stuart Halloway for developing this:
; https://github.com/johnlawrenceaspden/hobby-code/blob/master/fractaltree-stuart-halloway.clj
(ns fractaltree.fractaltree
  (:import 
    [javax.swing JFrame JPanel ]
    [java.awt Color Graphics Graphics2D])
  (:gen-class
    :main -main))

(defn draw-tree [ #^Graphics g2d angle x y length branch-angle depth]
   (when (> depth 0)
     (let [angle (double angle)
           x (double x)
           y (double y)
           length (double length)
           branch-angle (double branch-angle)
           depth (double depth)
           new-x (- x (* length (Math/sin (Math/toRadians angle))))
           new-y (- y (* length (Math/cos (Math/toRadians angle))))
           ;; new-length (fn [] (* length (+ 0.75 (rand 0.1))))
           ;; new-angle  (fn [op] (op angle (* branch-angle (+ 0.75 (rand)))))
           new-angle1  (+ angle (* branch-angle (+ 0.75 (rand))))
           new-angle2  (- angle (* branch-angle (+ 0.75 (rand))))
           new-length1 (* length (+ 0.75 (rand 0.1)))
           new-length2 (* length (+ 0.75 (rand 0.1)))
]
       (. g2d drawLine x y new-x new-y)
       (draw-tree g2d new-angle1 new-x new-y new-length1 branch-angle (- depth 1))
       (draw-tree g2d new-angle2 new-x new-y new-length2 branch-angle (- depth 1)))))

(defn render [ #^Graphics g w h]
  (doto g
    (.setColor (Color/BLACK))
    (.fillRect 0 0 w h)
    (.setColor (Color/GREEN)))
  (let [init-length ( / (min w h) 5),
        branch-angle (* 10 (/ w h)),
        max-depth 12]
    (draw-tree  g 0.0 (/ w 2) h init-length branch-angle max-depth)))

(defn create-panel []
    "Create a panel with a customised render"

  (proxy [JPanel] []
    (paintComponent [g]
                    (proxy-super paintComponent g)
                    ;(System/gc)
                    (time (render g (. this getWidth) (. this  
getHeight))))))

(defn run []
  (let [frame (JFrame. "Clojure Fractal Tree")
        panel (create-panel)]
    (doto frame
      (.add panel)
      (.setSize 640 400)
      (.setVisible true))))

(defn -main []
  (run) )