import java.util.ArrayList;

class Gene 
{
    
    // Instances for the gene sequence, beginning and the end of genes
    private ArrayList<String> aminoAcidSequence;
    private int beginingPosition;
    private int finalPosition;
    private int geneLength;

    /**
     * Constructs a Gene object with the provided amino acid sequence, beginning position,
     * final position, and gene length.
     *
     * @param aminoAcidSequence- amino acid sequence of the gene
     * @param beginingPosition - starting nucleotide position of the gene
     * @param finalPosition - ending nucleotide position of the gene
     * @param geneLength - length of the gene
     * @author
     */
    public Gene(ArrayList<String> aminoAcidSequence, int beginingPosition, int finalPosition, int geneLength) 
    {
        this.aminoAcidSequence = aminoAcidSequence;
        this.beginingPosition = beginingPosition;
        this.finalPosition = finalPosition;
        this.geneLength = geneLength;
    }

    /**
     * Retrieves the amino acid sequence of the gene.
     *
     * @return the amino acid sequence as an ArrayList of strings
     */
    public ArrayList<String> getAminoAcidSequence() 
    {
        return aminoAcidSequence;
    }
    
    /**
     * Retrieves the starting position of the gene within the sequence.
     *
     * @return the starting nucleotide position of the gene
     * @author
     */
    public int getBeginingPosition() 
    {
        return beginingPosition;
    }

    /**
     * Retrieves the final nucleotide position of the gene within the sequence.
     *
     * @return the final nucleotide position of the gene
     * @author
     */
    public int getFinalPosition() 
    {
        return finalPosition;
    }
    
    /**
     * Retrieves the codon length of the gene.
     *
     * @return the codon length of the gene
     * @author
     */
    public int getGeneLength() 
    {
        return geneLength;
    }
}