JLogic
======

(Java) A quick and dirty implementation that parses and evaluates a logical expression in post-fix form.

From left to right, the binary expressions (apart of a bigger expression) are extracted and evaluated. The result is inserted into its place. This is done until the expression is gone. E.g.

	(P&Q)|R, where P, Q, and R are all true 
  
  	[postfix] => PQ&R|
  
  	[substitution] => TT&T| 
  
  	[evaluation] => TT&T| => TT| => T		

& and | are the only operators defined.
P, Q, R are the only variables defined.

Verify results at http://www.oriontransfer.co.nz/learn/truth-table-solver/index
