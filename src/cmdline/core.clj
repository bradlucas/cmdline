(ns cmdline.core
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))



(defn -main0
  "The application's main function"
  [& args]
  (if args
    (println (str "You passed in this value: " args))
    (println "Usage: cmdline VALUE")))

(defn -main [& args]
  (let [[opts args banner]
        (cli args
             ["-h" "--help" "Show help" :flag true :default false]
             ["-d" "--delay" "Delay between messages (seconds)" :default 2]
             ["-f" "--from" "REQUIRED: From address)"]
             ["-e" "--email-file" "REQUIRED: Email addresses FILE)"]
             ["-s" "--subject" "REQUIRED: Message subject"]
             ["-m" "--message-file" "REQUIRED: Message FILE"]
             ["-b" "--bcc" "BCC address"] ;; optional
             ["-t" "--test" "Test mode does not send" :flag true :default false]
             )]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (if
        (and
         (:from opts)
         (:email-file opts)
         (:subject opts)
         (:message-file opts))
      (do
        (println "")
        (run opts))
      (println banner))))
