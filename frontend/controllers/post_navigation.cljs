(ns frontend.controllers.post-navigation
  (:require [cljs.core.async :as async :refer [>! <! alts! chan sliding-buffer put! close!]]
            [clojure.string :as string]
            [frontend.utils :as utils :refer [mlog]]))

(defn set-page-title! [& [title]]
  (set! (.-title js/document) (if title
                                (str title  " - CircleCI")
                                "CircleCI")))

(defmulti post-navigated-to!
  (fn [target to args previous-state current-state] to))

(defmethod post-navigated-to! :default
  [target to args previous-state current-state]
  (mlog "No post-nav for: " to))

(defmethod post-navigated-to! :root
  [target to [project-id build-num] previous-state current-state]
  (set-page-title!))

(defmethod post-navigated-to! :build-inspector
  [target to [project-id build-num] previous-state current-state]
  (set-page-title! (str project-id " #" build-num)))
