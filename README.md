# lottery

Solution to the lottery problem described below.

Non-functional requirements:

- The lottery should have 50 tickets (numbered 1 to 50)
- The lot for the prizes should be 50% of the amount in the bank
- The prizes should be calculated as follows:

   1st prize = 75% of the lot
   2nd prize = 15% of the lot
   3rd prize = 10% of the lot

   For example, if there was a draw in this moment, and the
   amount in the bank was of $200, the prizes would be distributed
   as follows:

   $75 for the 1st prize
   $15 for the 2nd prize
   $10 for the 3rd prize

- The price of an individual ticket should be $10
- Three tickets should be drawn randomly and awarded the prizes
  described above

Functional requirements:

- I want to be able to buy a ticket for a draw by providing a name. When
  buying a ticket, the number of the ticket should be displayed on the 
  screen.

- I want to be able to perform a draw

- I want to be able to display the winning tickets as follows:

  |-------------------|-------------------|-------------------|
  |1st prize ticket   |2nd prize ticket   |3rd prize ticket   |
  |-------------------|-------------------|-------------------|
  |Winner name : $75  |Winner name : $15  |Winner name : $10  |
  |-------------------|-------------------|-------------------|

Notes:

- The command line is good enough as interface for the user
- The functionality described above should be called by the "buy",
  "draw" and "winners" commands respectively.
- When starting the program, the amount in the bank can be initialized
  to $200
- Amounts can be rounded or truncated

## Usage

### Starting the program

At the shell type:

$ lein repl

The lottery client prompt should show up:

"REPL started; server listening on localhost:25948."
lottery.client=> 

### The "buy" command

At the repl prompt type:

lottery.client=> (buy "John")
John, thank you for buying a ticket. Your ticket number is 37.

### The "draw" command

At the repl prompt type:

lottery.client=> (draw)
Draw performed.

### The "winners" command

At the repl prompt type:

lottery.client=> (winners)
|-------------------|-------------------|-------------------|
|Ticket #25         |Ticket #1          |Ticket #41         |
|-------------------|-------------------|-------------------|
|Not purchased : $79|Not purchased : $16|Not purchased : $11|
|-------------------|-------------------|-------------------|

## Installation

$ lein deps

## License

Copyright (C) 2010 Enrique Lopez de Lara

Distributed under the Eclipse Public License, the same as Clojure.
