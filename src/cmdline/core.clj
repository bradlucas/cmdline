(ns cmdline.core
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))



(defn -main0
  "The application's main function. As an example this is the original -main which shows how you can use args directly"
  [& args]
  (if args
    (println (str "You passed in this value: " args))
    (println "Usage: cmdline VALUE")))


(defn run
  "Print out the options and the arguments"
  [opts args]
  (println (str "Options:\n" opts "\n\n"))
  (println (str "Arguments:\n" args "\n\n")))


(defn -main [& args]
  "More advanced -main which uses clojure.tools.cli to process arguments.
Note, that cli returns three values
opts is a map with your arguments which are flags and their values
args is the arguments which are not flags
banner is a prettied up string which you can print to the user if there is a problem with the input

"
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
        (run opts args))
      (println banner))))
