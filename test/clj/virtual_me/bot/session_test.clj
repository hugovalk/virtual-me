(ns virtual-me.bot.session-test
  (:use midje.sweet
        virtual-me.bot.session))

(fact-group "The InMemoryChatSessionStore"
  (fact "Can create and store a new session"
    (let [store (atom {})
          repo (init-inmemory-chat-session-store store)
          session (new-session repo "Hugo")
          session-id (:session-id session)]
      ((keyword session-id) @store) => {:session-id session-id :name "Hugo"}))

  (fact "Can retrieve an earlier stored session"
    (let [repo (init-inmemory-chat-session-store)
          session (new-session repo "Hugo")
          result (load-session repo (:session-id session))]
      (:name result) => "Hugo"))

  (fact "Can create and store a new session"
    (let [store (atom {})
          repo (init-inmemory-chat-session-store store)
          session (new-session repo "Hugo")
          session-id (delete-session repo (:session-id session))]
      (count @store) => 0)))
