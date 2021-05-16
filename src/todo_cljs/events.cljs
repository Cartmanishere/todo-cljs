(ns todo-cljs.events
  (:require
   [re-frame.core :as re-frame]
   [todo-cljs.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::update-input
 (fn [db event]
   (let [text (second event)]
     (assoc db :task-input text))))

(defn create-task
  [id body]
  {:state :open
   :body body
   :id id})


(re-frame/reg-event-db
 ::add-task
 (fn [db _]
   (let [text (:task-input db)
         id (inc (:task-counter db))
         task (create-task id text)]
     (-> db
         (assoc :task-input "")
         (update :todos conj task)
         (update :task-counter inc)))))


(re-frame/reg-event-db
 ::delete-task
 (fn [db event]
   (let [id (second event)]
     (update db
             :todos
             (partial filter #(not= (str (:id %))
                                    id))))))
