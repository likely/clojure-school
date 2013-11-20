;; Update the handler to return a web page at
;; http://localhost:3000/ping that returns "pong"

(ns clojureschool.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/ping" [] "pong")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))


;; Update the handler to return a web page at
;; http://localhost:3000/list/[list_id]?n=21
;; that returns a page with "List: [list id]" in the title
;; and a list of integers from 1 to n.

(ns clojureschool.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :refer [html5]]))

(defn list-view [list-id n]
  (html5
   [:body
    [:h1 (str list-id)]
    [:ul (for [x (range n)]
           [:li (str "Item " x)])]]))

(defroutes app-routes
  (GET "/list/:list-id" [list-id n]
       (list-view list-id (read-string n)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

;; Start a thread (future) to sum all the numbers to one hundred million

(def big-sum
  (future
    (reduce + (range 100000000))))

@big-sum

;; Create a function which appends the numbers 1-10 to a vector in an atom (hint: dotimes)

(def vector-atom (atom []))

(defn add-to-vector [vector]
  (dotimes [n 10]
    (swap! vector conj n)))

;; Run the function across 10 threads simultaneously and observe the output

(dotimes [_ 10]
  (future (add-to-vector vector-atom)))

;; Extra credit: write a function that confirms you have ten of each number

(frequencies @vector-atom)

;; Add a visitor counter to the bottom of your web page that increments for each visit

(ns clojureschool.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :refer [html5]]))

(def counter (atom 0))

(defn counter-view [n]
  (html5
   [:body
    [:h1 "Welcome"]
    [:p (str "You're visitor number " n)]]))

(defroutes app-routes
  (GET "/" [] (counter-view (swap! counter inc)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
