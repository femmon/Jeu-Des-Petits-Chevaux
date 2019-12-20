Game specs: 
Rules:
	In the beginning, roll one dice to get player order.   
Dice rules:  
	if  one dice equal to “6” or two dice have the same number, can summon new horse from the nest and one extra turn  
	All dice rolls have to be taken and cannot be forfeited.  
	Can move with one dice or both dices  
Gameplay rules:   
	Kick: If a horse from a player coincides with another player’s horse, that horse shalled be kicked back to its nest. [1]  
	Block: if the dices’ value is not enough for a horse to get past any horse, then it stays there and it is considered lost turn.   
	Home run: once the horse goes through the whole board 360º, then it is eligible for entering the home path.   
	Home path: The horse gets assigned to a position on the path based on the dice that the user chooses. [2]
	Rule of winning:  
		    If all four horses of a player are all allocated on the home path in their         respective positions ( 6, 5, 4, 3), that player is declared victor. [3]  
Software documentation:  
	Score: [4]   
	0 points are shown on nests at the beginning  
	Kicking = 2 points [1]  
	Points are given based on the home position it’s standing on. 1 point is also awarded for moving to the next home position ( e.g. 5 -> 6 ) [2]   
	Winner gets 6 + 5 + 4 + 3 = 18 points [3]  
	Saved up for next game  
MVC implementation:   
Model: Dice, horse, nest, homepath, player,   
	Controller: homepath, path, nest  
	View: TBA  
	Functional requirements:   
	Rolling dices: Animation optional. Display of dices required.   
	Moving horses:  Select a piece and move it.   
	Pause game:    
	Score: [4]
	Sound FX: Make sound for regular move, move to nest, move to home position, etc.  
	Play again or quit:  
		If player plays again, then score saved and choose players.   
		If player quits, then score = 0.  
			
Stats:  
	Display current scores, highlight turn, move, etc.   
Languages:  
	English and VNmese.   
Advanced:
	Cross platform gaming  
AI:
	Merely automating actions.   
