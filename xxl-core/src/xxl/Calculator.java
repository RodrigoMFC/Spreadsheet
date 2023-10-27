package xxl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

import xxl.exceptions.ImportFileException;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;
import xxl.exceptions.UnrecognizedEntryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {

    /** The current spreadsheet. */
    private Spreadsheet _spreadsheet = null;

    private String _filename="";
    private List<User> users = new ArrayList<>();
    private User user = new User("root");
    // FIXME add more fields if needed
    public Calculator(){
        users.add(user);
    }
    public boolean changed(){
        return _spreadsheet.getChanged();
    }
    public void New(int numLin, int numCol){
            _spreadsheet= new Spreadsheet(numLin, numCol,user);
            _spreadsheet.setChanged(true);
            user.addSpreadsheet(_spreadsheet);
    }
    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        _spreadsheet.setChanged(false);    
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
            oos.writeObject(_spreadsheet);
            oos.close();
    }

    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        // FIXME implement serialization method
        _filename = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(String filename) throws UnavailableFileException,IOException,ClassNotFoundException {
        _filename = filename;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
        _spreadsheet = (Spreadsheet) ois.readObject();
        ois.close();
        }
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line =reader.readLine();
            int num1=Integer.parseInt(line.split("\\=")[1]);
            line =reader.readLine();
            int num2=Integer.parseInt(line.split("\\=")[1]);
            _spreadsheet= new Spreadsheet(num1, num2,user);
            user.addSpreadsheet(_spreadsheet);
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                _spreadsheet.insertContents(fields[0],fields[1]);
            }
        // ....
        } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
            throw new ImportFileException(filename, e);
        }
    }

    /** @return current instance of spreedsheet. */
    public Spreadsheet getSpreadsheet() {
        return _spreadsheet;
    }


    public void reset(){
        _spreadsheet= null;
    }
    //funcoes para implementar operacoes (funcoes chamadas no main).


    public void Show(String range) throws UnrecognizedEntryException {
        _spreadsheet.showContents(range);
    }

    public void DoSave() {
    }

}