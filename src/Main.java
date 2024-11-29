//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        String menuChoice;
        boolean needsToBeSaved = false;
        ArrayList<String> myArrList = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        String fileName = "";

        //It does say there is an error in my program, but I have yet to encounter it in any fashion despite running it multiple times.
        //Now there needs to be a do while loop that will "trap" the user until they decide to exit.
        do {
            menuChoice = "";
            menuChoice = SafeInput.getRegExString(in, "Do you want to: Add something to the list? Delete an item from the list? Move an item in the list? Insert an item into the list?\n Open a new file? View the list? Save the list? Clear the list? Quit the program? [A,D,M,I,O,V,S,C,Q]", "[AaDdMmIiOoVvSsCcQq]");

            //System.out.println(); //Clear line to break things up


            switch (menuChoice) {
                case "A", "a":
                    needsToBeSaved = addOption(in, myArrList, needsToBeSaved);
                    break;
                case "D", "d":
                    needsToBeSaved = deleteOption(in, myArrList, needsToBeSaved);
                    break;
                case "I", "i":
                    needsToBeSaved = insertOption(in, myArrList, needsToBeSaved);
                    break;
                case "V", "v":
                    viewOption(myArrList,fileName);
                    break;
                case "Q", "q":
                    done = quitOption(in, needsToBeSaved);
                    break;
                case "O", "o":
                    needsToBeSaved = openOption(chooser, in, myArrList, needsToBeSaved, fileName);
                    break;
                case "M", "m":
                    needsToBeSaved = moveOption(in, myArrList, needsToBeSaved);
                    break;
                case "S", "s":
                    needsToBeSaved = saveOption(in, myArrList, needsToBeSaved, fileName);
                    break;
                case "C", "c":
                    needsToBeSaved = clearOption(myArrList, needsToBeSaved);
                    break;
            }



            //System.out.println(); //clearing a bit of area before printing the current list
            //viewOption(myArrList); //Removed because it feels like it invalidates the print menu option and might rapidly spam output box at large list size.
            System.out.println(); //Line here might make things look less congested.
        } while(!done);
    }

    public static boolean quitOption(Scanner in, boolean needsToBeSaved)
    {
        boolean quit = false;
        if(!needsToBeSaved)
        {
            quit = SafeInput.getYNConfirm(in, "Are you sure that you want to quit? [Y/N]");
            return quit;
        }
        else
        {
            quit = SafeInput.getYNConfirm(in, "You have unsaved work. Are you sure that you want to quit? [Y/N]");
            return quit;

        }
    }

    public static void viewOption(ArrayList<String> myArrList, String fileName)
    {
        if(!myArrList.isEmpty())
        {
            System.out.println("Viewing: " + fileName);
            for (int x = 0; x < myArrList.size(); x++) {
                System.out.println("\n" + (x + 1) + ": " + myArrList.get(x)); //Trying to make it user-friendly for those who don't count from zero.
            }
        }
        else
        {
            System.out.println("\nNothing to print.");
        }
    }

    public static boolean deleteOption(Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        int removalItem;

        if (myArrList.isEmpty())
        {
            System.out.println("List is empty. Nothing to remove.");
        }
        else { //Something says there is an error at 95, but the method seems to work fine?
            removalItem = SafeInput.getRangedInt(in, "Removing something from the list. Please enter a number between 1 and " + myArrList.size(), 1, myArrList.size());
            myArrList.remove(removalItem - 1);
            System.out.println("\nItem successfully removed from the list.");
            needsToBeSaved = true;
            return needsToBeSaved;
        }
        return needsToBeSaved;
    }

    public static boolean insertOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        int insertIndex;
        String insertItem;

        insertIndex = SafeInput.getRangedInt(in, "To insert an item, please enter a number between 1 and " + myArrList.size(), 1, myArrList.size());
        insertItem = SafeInput.getNonZeroLenString(in, "Please enter an item into the list");

        myArrList.add((insertIndex -1), insertItem);
        needsToBeSaved = true;
        return needsToBeSaved;
    }

    public static boolean addOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        myArrList.add(SafeInput.getNonZeroLenString(in,"Please enter a name"));
        //Feels like a waste to make a one line method that contains usage of a method.
        //But it is by the rubric.
        needsToBeSaved = true;
        return needsToBeSaved;
    }

    public static boolean openOption (JFileChooser chooser, Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved, String fileName)
    {

        boolean userSaveOverride = false;
        String rec = "";
        File selectedFile;
        if (needsToBeSaved)
        {
            userSaveOverride = SafeInput.getYNConfirm(in, "You have unsaved work. Are you sure that you want to open a new file? [Y/N]");
            if (userSaveOverride) //This should be mostly the same as the option to open with no need to save. I could probably clean this up by making the interior a separate method.
            { //But I am not certain how the imported values would be affected by that and what I would need to do
                try
                {

                    File workingDirectory = new File("C:\\Users\\jhott\\IdeaProjects\\Lab_13_FileListMaker\\src");

                    chooser.setCurrentDirectory(workingDirectory);

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        selectedFile = chooser.getSelectedFile();
                        Path file = selectedFile.toPath();
                        fileName =selectedFile.getName();

                        InputStream is =
                                new BufferedInputStream(Files.newInputStream(file, CREATE));
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(is));

                        int line = 0;
                        while (reader.ready()) {
                            rec = reader.readLine();
                            myArrList.add(rec);
                            line++;
                            // echo to screen
                            System.out.printf("\nLine %4d %-60s ", line, rec);
                        }
                        reader.close();
                        System.out.println("\nData file read.");
                    } else {
                        System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                    }
                }
                catch (FileNotFoundException e)
                {
                System.out.println("File not found!!!");
                e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                needsToBeSaved = false;
                return needsToBeSaved;
            }
            else
            {
                return needsToBeSaved; //They don't want to override here, so this should be the end for this train of logic?
            }
        }
        else
        {
            try
            {
                File workingDirectory = new File("C:\\Users\\jhott\\IdeaProjects\\Lab_13_FileListMaker\\src");

                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();
                    fileName =selectedFile.getName();

                    InputStream is =
                            new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(is));

                    int line = 0;
                    while (reader.ready()) {
                        rec = reader.readLine();
                        myArrList.add(rec);
                        line++;
                        // echo to screen
                        System.out.printf("\nLine %4d %-60s ", line, rec);
                    }
                    reader.close();
                    System.out.println("\nData file read.");
                } else {
                    System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found!!!");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            needsToBeSaved = false;
            return needsToBeSaved;
        }
    }

    public static boolean moveOption (Scanner in, ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        if(myArrList.isEmpty())
        {
            System.out.println("Nothing inside list. Cannot move anything.");
            return needsToBeSaved; //program made me put it here because it was unhappy
        }
        else
        {
            int oldListLocation = SafeInput.getRangedInt(in, "Enter the location of the item to move (1 to " + myArrList.size() + "): ", 1, myArrList.size());
            int newListLocation = SafeInput.getRangedInt(in, "Enter the new location for the item (1 to " + myArrList.size() + "): ", 1, myArrList.size());

            String itemToMove = myArrList.remove(oldListLocation - 1);
            myArrList.add(newListLocation - 1, itemToMove);
            needsToBeSaved = true;
            System.out.println("Item moved successfully.");
            return needsToBeSaved;
        }
    }

    public static boolean saveOption (Scanner in,ArrayList<String> myArrList, boolean needsToBeSaved, String fileName)
    {
        fileName = SafeInput.getNonZeroLenString(in, "Enter the desired filename");
        fileName = fileName + ".txt";

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src", fileName);

        try
        {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : myArrList)
            {
                writer.write(rec, 0, rec.length());

                writer.newLine();
            }
            writer.close();
            System.out.println("Everything has been printed");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return needsToBeSaved;
    }

    public static boolean clearOption (ArrayList<String> myArrList, boolean needsToBeSaved)
    {
        if (myArrList.isEmpty())
        {
            System.out.println("\nList is empty. Nothing to remove.");
        }
        else
        {
            for(int i = myArrList.size(); i >= 1; i--)
            {
                myArrList.remove(i-1);
            }

            System.out.println("\nList Cleared.");

        }
        needsToBeSaved = true;
        return needsToBeSaved;
    }
}