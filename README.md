# morris-game
collection of small classes to find an optimal move in a variation of morris game. (JAVA)
the board setup can be seen in board.png
the input of all the programs is a string of W(for white piece) B(for Black piece) or x(for no piece).
example:
BxBxWWBWWBxxWBWWxBBxx
each char coresponds to a squre on the board, going from left to right(1-20 in board.png)
output is the augmented string after a move was found. Also shows evaluation. 
some of the code use MinMax algorithem, and some does alpha beta pruning. 
