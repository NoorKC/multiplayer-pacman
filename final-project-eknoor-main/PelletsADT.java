import java.awt.Rectangle;

public class PelletsADT {
   
        // create the variables that cannot be changed by the user
        // the beginning array
        private Rectangle[] list; 
        // spots in the array
        private int listNum;
     
        public PelletsADT(){
           // give the variables values
           this.listNum = 0;
           this.list = new Rectangle[5];
        }
     
        // if no more space in the array, make the array bigger
        public void expandArray(){
           // make a new array, twice as big 
           Rectangle[] biggerList = new Rectangle[this.list.length * 2];
           // transfer all values to the new array
           for(int i = 0; i < this.list.length; i++){
              biggerList[i] = this.list[i];
           }
           // replace the bigger array for the original one
           this.list = biggerList;
        }
     
        // add a value to the back of the line
        public void add(int x, int y, int size){
           // check if the array is already full
           // if it is, call expandArray to make it bigger
           if(this.listNum == this.list.length){
              expandArray();
           }
           // add the value to the array
           this.list[this.listNum] = new Rectangle(x,y,size,size);
           // add to the listNum because one more space is filled now
           this.listNum++;
        }      
     
        // sort of like peek, get the value at the specific index
        public Rectangle get(int index){
           // if they want the 1st one, that is the 0th position so subtract 1
           return this.list[index];
        }
     
        // remove the value at a certain index
        public Rectangle remove(int index){
           // store the value the user wants to remove (so you can return it later)
           Rectangle removed = this.list[index];
           // move over all the values one value back from where the index is removed 
           // don't actually need to remove the value at index but just replace it 
           for(int i = index; i < this.listNum - 1; i++){
              this.list[i] = this.list[i + 1];
           }
           this.listNum--;
           // return the original value at the index
           return removed;
        }
     
        // how big is the array?
        public int size(){
           return this.listNum;
        }
     
        // is the list empty?
        public boolean isEmpty(){
           // if the number of spots taken in the array is 0, the array is empty so return true
           return (this.listNum == 0);
        }
     }
