import java.util.ArrayList;

public class AminoAcid 
{
    private String name;
    private String letterCode;
    private ArrayList<String> codon = new ArrayList<String>();

    /**
     * Constructs an AminoAcid object with the specified name and letter code.
     *
     * @param name - name of the amino acid
     * @param letterCode - single-letter code representing the amino acid
     * @author Malik 
     */
    public AminoAcid(String name, String letter)
    {
        this.name = name;
        letterCode = letter;
    }

    /**
     * Adds a codon to the list of codons for this amino acid.
     *
     * @param x - the codon to add
     * @author Malik 
     */
    public void setCodons(String x)
    {
        codon.add(x);
    }

    /**
     * Retrieves the name of this amino acid.
     *
     * @return the name of the amino acid
     * @author Malik
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Retrieves the letter code of this amino acid.
     *
     * @return the letter code of the amino acid
     * @author Malik
     */
    public String getLetterCode()
    {
        return letterCode;
    }
    
    /**
     * Retrieves the list of codons associated with this amino acid.
     *
     * @return the list of codons associated with this amino acid
     * @author Malik
     */
    public ArrayList<String> getCodon()
    {
    	return codon;
    }

    /**
     * Retrieves a string representation of the codons associated with this amino acid.
     *
     * @return a string containing the codons associated with this amino acid, separated by spaces
     * @author Malik
     */
    public String getCodons() 
    {
    	String codonString = "";
        for (String currentCodon : codon) {
            codonString += currentCodon + " ";
        }
        return codonString;
    }

    /**
     * Retrieves the number of codons associated with this amino acid.
     *
     * @return the number of codons associated with this amino acid
     * @author Malik
     */
    public int getCodonSize()
    {
        return codon.size();
    }

    /**
     * Retrieves the individual codon at the specified index.
     *
     * @param x the index of the codon to retrieve
     * @return the codon at the specified index
     * @author Malik
     */
      public String getIndividualCodon(int x)
	{
    	if(x>getCodonSize()||x<0)
        {
            return "The number you input is out of bounds for the ArrayList of codons";
        }
		return codon.get(x); 
	}

    /**
     * Returns a string representation of the amino acid object.
     *
     * @return a string containing the name, letter code, and codons of the amino acid
     * @author Malik
     */
    public String toString()
    {
        String i= "Name: " + name + ", Letter code: " + letterCode + ", Codons: " + getCodons();
        return i;
    }   
}
