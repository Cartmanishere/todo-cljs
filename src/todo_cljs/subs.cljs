(ns todo-cljs.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::update-input
 (fn [db]
   (:task-input db)))


(re-frame/reg-sub
 ::add-task
 (fn [db]
   (:todos db)))

(re-frame/reg-sub
 ::delete-task
 (fn [db]
   (:todos db)))
