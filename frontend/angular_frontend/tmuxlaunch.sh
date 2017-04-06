SESSION=dev
tmux new-session -d -s $SESSION
tmux new-window -t $SESSION:1 -n 'webserver'

tmux select-window -t $SESSION:1
tmux send-keys 'atom .' C-m
tmux split-window -h
tmux send-keys 'grunt serve' C-m

tmux attach -t $SESSION
