# N-Queens problem

You have board *n* x *n*. You have to place *n* queens so that no one of them have conflicts with the others.

## Usage
- input - the size of the field
- output - result.txt containg the solution (or in the console - printing line commented)

## Solution
We have a matrix of integers containg the number of conflicts for each field. Every queen is represented by -1.

The algorithm tries to change the place of worse queen (the one with max conflicts) in its column. It stops when the board is ready, or maximum number of tries has been done.

## Example of solution:
```
_ _ * _ _ 
* _ _ _ _ 
_ _ _ * _ 
_ * _ _ _ 
_ _ _ _ * 
```