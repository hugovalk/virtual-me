(ns virtual-me.util.config-test
  (:use midje.sweet
        virtual-me.util.config))

(facts "Configuration loader tests"
       (fact "Properties are loaded from disk"
             (config-get :test-prop :deeper-prop) => "deeper-value"))

