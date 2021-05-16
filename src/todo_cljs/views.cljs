(ns todo-cljs.views
  (:require
   [re-frame.core :as re-frame]
   [todo-cljs.subs :as subs]
   [todo-cljs.events :as events]))


(defn todo-input [text]
  [:div.todo__add-form.pure-u-3-5
   [:form.pure-form
    [:input.pure-input-1
     {:type :text
      :name :todo
      :value text
      :placeholder "Add some todo"
      :on-change (fn [e]
                   (re-frame/dispatch [::events/update-input e.target.value]))}]]])

(defn add-todo []
  (let [text @(re-frame/subscribe [::subs/update-input])]
    [:div.todo__add.pure-g.center
     [todo-input text]
     [:div.todo__add-button.pure-u-1-5
      [:button.pure-button.pure-button-primary
       (merge {:on-click (fn [e]
                           (js/console.log "Clicked button")
                           (re-frame/dispatch [::events/add-task]))}
              (when (= text "")
                {:disabled true}))
       "Add Task"]]]))


(defn todo-item [todo]
  [:tr
   {:key (:id todo)}
   [:td (:id todo)]
   [:td (:body todo)]
   [:td (:state todo)]
   [:td
    [:button.todo__button.todo__button--red.pure-button
     {:id (:id todo)
      :on-click (fn [e]
                  (re-frame/dispatch [::events/delete-task e.target.id]))}
     "Delete"]]])

(defn todo-table []
  (let [todos @(re-frame/subscribe [::subs/add-task])]
    (when-not (zero? (count todos))
      [:div.todo__list.pure-g
       [:div.pure-u-1
        [:table.pure-table {:style {:margin "auto"}}
         [:thead
          [:tr
           [:th "#"]
           [:th "Task"]
           [:th "Status"]
           [:th "Action"]]]
         [:tbody
          (map todo-item todos)]]]])))


(defn intro []
  [:div.todo__title
   [:h1
    "Welcome to the simple todo App"]])

(defn main-panel []
  [:div
   [intro]
   [add-todo]
   [todo-table]])
