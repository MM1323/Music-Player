// Java orogram to demonstrate working of Queue
// interface in Java
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.lang.Math;

public class Melody {
  private Queue<Note> MainSong = new LinkedList<Note>();
  private double totalLength = 0;
  private double TotalSize = 0;

  public Melody(QueueInterface<Note> song){
    int size = song.size();
    Note Note = null;
    boolean Inside = false;

    for (int i = 0; size > i; i++) {
      Note = song.dequeue(); //removes the note from song
      MainSong.add(Note);  //add it to the MainSong

      if (Note.isRepeat()) { //changes if we are inside of a repeat section
        Inside = !Inside;
      }

      if (Inside) { //if we are in a repeat section the multiply the length
        totalLength += Note.getDuration() * 2;
        TotalSize++;
      } else if (Note.isRepeat()){ //chaches the last repeat that was missed above
        totalLength += Note.getDuration() * 2;
        TotalSize++;
      } else { //calulates notes not in the repeat section
        totalLength += Note.getDuration(); //get the duration of the each of the notes and add it together
        TotalSize++;
      }
    }
    totalLength = Math.round(totalLength  * 10.0) / 10.0;
    //System.out.println(TotalSize);
  }

  /* Returns the total length in sec. These can be calculated when initially adding it to the MainQue in a separate variable */
  public double getTotalDuration() {
    return totalLength;
  }


  /* Returns a string representation of the notes by looping through the song */
  public String toString() {
    String SongString = "";
    for (int i = 0; i < TotalSize; i++){
      Note note = MainSong.remove();
      SongString += note + "\n";
      MainSong.add(note);
    }
    return SongString;
  }

  /*Take in a double and goes through the whole song and changes the tempo of each of the notes.
    In the end, there is a recalculation of the totalLength of the song.*/
  public void changeTempo(double tempo) {
    double change_temp = 0;
    for (int i = 0; i < TotalSize; i++) {
      Note note = MainSong.remove();
      note.setDuration(note.getDuration() * tempo);
      MainSong.add(note);
    }
    totalLength = totalLength * tempo;
  }

  /* Reverses the contents of the main song by using temperory stack and then adding it back into the
    the MainSong queue*/
  public void reverse() {
    Stack<Note> stack = new Stack<Note>();
     while (!MainSong.isEmpty()) {
         stack.add(MainSong.peek());
         MainSong.remove();
     }
     while (!stack.isEmpty()) {
         MainSong.add(stack.peek());
         stack.pop();
     }
  }

  /* Addes another melody by acessing the other's MainSong and adding to the
    current one*/
  public void append(Melody other) {
    for (int i = 0; i < other.TotalSize; i++) {
      Note note = other.MainSong.remove();
      MainSong.add(note);
      other.MainSong.add(note);
    }
  }


  /* Goes through the whole song using a for loop. When the note is a repeat, it is added to the repeat Queue
    and goes through it until another repeat is found. It then plays the repeat section again after the first loop is
    over. If not a repeat, the note is played naturally  */
  public void play() {
    int RepeatNumber = 0;
    for (int i = 0; i < TotalSize; i++) { //for loop for the whole song
        Note note = MainSong.remove();
        MainSong.add(note);
        note.play();

        if (note.isRepeat()) { //checks if the note is repeated
            if (RepeatNumber == 0) {
                Queue < Note > RepeatSection = new LinkedList < Note > (); //initializes the repeat section
                RepeatSection.add(note);
                note.play();
                note = MainSong.remove();
                i++;

                while (!(note.isRepeat())) { //adds the notes to repeat until another true is found
                    MainSong.add(note);
                    RepeatSection.add(note);
                    note.play();
                    note = MainSong.remove();
                    i++;
                }
                RepeatSection.add(note);
                MainSong.add(note);

                for (int x = 0; x < RepeatSection.size(); x++) { //plays the repeated section
                    note = RepeatSection.remove();
                    note.play();
                    RepeatSection.add(note);
                }
            }
            RepeatNumber++;
        }
      }
    }


}
