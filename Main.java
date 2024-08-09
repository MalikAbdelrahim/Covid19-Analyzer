import java.util.Scanner; 
import java.io.*;
import java.util.ArrayList;
public class GeneAnalysis {
	public static void main(String[] args) throws IOException{
		
	Scanner kbd = new Scanner(System.in);
	String RFFile = "";
	
	// User chooses file
	System.out.println("****Covid-19 Genome Analysis****");
	System.out.print("Which reading frame would you like to analyze(1, 2, or 3)? ");
	int readingFrame = kbd.nextInt();
	switch(readingFrame)
	{
		case 1: RFFile = "measlesSequenceRF1.csv";
		break;
		case 2: RFFile = "covidSequenceRF2.csv";
		break;	
		case 3: RFFile = "covidSequenceRF3.csv";
		break;
	}
	
	//User chooses action
	System.out.println("Which action would you like to perform?");
	System.out.println("1. Gene Analysis Report");
	System.out.println("2. Codon Bias Analysis");
	int actionChoice = kbd.nextInt();
	int choiceTwo = 0;
	if (actionChoice == 2)
	{
		System.out.println("Which would you like to do?");
		System.out.println("1. Complete Amino Acid Codon Bias Report");
		System.out.println("2. Individual Amino Acid Codon Bias Analysis");
		choiceTwo = kbd.nextInt();
	}
	
	//read in file and tokenize the string into codons
	File file = new File(RFFile);
	Scanner infile = new Scanner(file);
	String str = "";
	
	while(infile.hasNext())
	{
		str += infile.nextLine();
	}
	infile.close();
	String[] tokens = str.split(",");
	int startPosition = 0, stopPosition = 0;
	
	//makes an array list of genes 
	ArrayList<Gene> geneSequences = new ArrayList<Gene>();
	if (actionChoice == 1) {
		//read token array until start codon is read 
		for(int i=0; i<tokens.length; i++)
		{
			if (tokens[i].equals("ATG"))
			{
				// once a start codon is read, an array list of codons in created
				ArrayList<String> aminoAcidSequence = new ArrayList<>();
				startPosition = i;
				aminoAcidSequence.add("ATG");
				// each codon is added to the array list until a stop codon is reached
				for(i = startPosition + 1; i<tokens.length; i++)
				{
					aminoAcidSequence.add(tokens[i]);
					if(tokens[i].equals("TAG")|| tokens[i].equals("TAA")|| tokens[i].equals("TGA"))
					{
						stopPosition = i;
						// once a stop codon is reached, the loop stops adding codons to the array list
						break;
					}
				}
				// checks for the gene length to be greater than or equal to 50
				if(stopPosition - startPosition >= 50)
				{
					// calculates gene length, start nucleotide, and stop nucleotide
					int geneLength = stopPosition - startPosition + 1;
					int startNucleotide = startPosition * 3, stopNucleotide = startNucleotide + (geneLength * 3);
					// adds a new gene of the previously found information into the first array list made
					geneSequences.add(new Gene(aminoAcidSequence, startNucleotide, stopNucleotide, geneLength));
				}
			}
		}
		// prints gene information
		int i = 1;
		for(Gene g: geneSequences)
		{
			System.out.println("Gene " + i + " (" + g.getGeneLength() + ") :");
			System.out.println(g.getBeginingPosition() + ".." + g.getFinalPosition());
			System.out.println("Sequence:");
			System.out.println(g.getGeneSequence());
			System.out.println();
			i++;
		}
	}
	else if(actionChoice == 2 && choiceTwo == 1)
	{
		
	}
	else if(actionChoice == 2 && choiceTwo == 2)
	{
		
	}
	else
		System.out.println("At least one invalid choice was made.");
	}
}
