/**
 * Lab 9: Covid-19 Genome Analysis
 * COSC 1437.200
 * @author Varunika, Malik, Samuel
 */

 import java.util.ArrayList;
 import java.util.Scanner;
 import java.io.*;
 
 public class CovidAnalysis {
	 public static void main(String[] args) throws IOException {
		 Scanner kbd = new Scanner(System.in);
		 String RFFile = "";
		 
		 System.out.println("****Covid-19 Genome Analysis****");
		 System.out.print("Which reading frame would you like to analyze(1, 2, or 3)?");
		 int readingFrame = kbd.nextInt();
		 RFFile = readingFrameSwitch(readingFrame);
		 printAnalysisMenu();
		 int actionChoice = kbd.nextInt();
		 String analysisFile = outputFileName(actionChoice, readingFrame);
		 PrintWriter outFile = new PrintWriter(analysisFile);
		 String[] codons = makeRFTokens(RFFile);
		 ArrayList<AminoAcid> listOFAA = buildTheAATable();
		 
		 if (actionChoice == 1)
		 {
			 outFile.println("**Gene analysis for file " + RFFile + "**");
			 ArrayList<Gene> geneSequences = findGenes(codons);
			 String singleLetterGene = "";
			 printGeneAnalysis(singleLetterGene, listOFAA, geneSequences, outFile);
			 System.out.println("A gene analysis report can be found at " + analysisFile + ".");
			 
		 }
		 else if (actionChoice == 2)
		 {
			 printCodonBiasMenu();
			 
			 outFile.println("*********** Complete Codon Bias Report **********");
			 // Begining the codon counting
			 for(AminoAcid loop: listOFAA)
			 {
				 codonAAReader(loop, codons, outFile);
			 }
			 outFile.close();
			 
			 int cbActionChoice = kbd.nextInt();
			 if (cbActionChoice == 2)
			 {
				 String aminoAcidCode;
				 do {
					 System.out.println("Enter the letter code of the amino acid you want to analyze (Enter 0 to quit):");
					 aminoAcidCode = kbd.next();
				 
					 // Find the AminoAcid object with the matching letter code
					 AminoAcid selectedAA = letterMatch(listOFAA, aminoAcidCode);
				 
					 if (selectedAA != null) {
						 // Perform the codon bias analysis for the selected amino acid
						 individualCodonBiasAnalysis(codons, selectedAA);
					 }
				 }while(!aminoAcidCode.equals("0"));
			 }
			 System.out.println("A complete codon bias report can be found at " + analysisFile + ".");
		 }
		 else
			 System.out.println("At least one invalid choice was made.");
		 outFile.close();
	 }
	 
	 /**
	  * Determines the reading frame file based on the given reading frame by the user.
	  *
	  * @param readingFrame - reading frame to analyze.
	  * @return The file name corresponding to the given reading frame.
	  * @author Varunika
	  */
	 public static String readingFrameSwitch(int readingFrame)
	 {
		 String file = null;
		 switch(readingFrame)
		 {
			 case 1: file = "covidSequenceRF1.csv";
			 break;
			 case 2: file = "covidSequenceRF2.csv";
			 break;	
			 case 3: file = "covidSequenceRF3.csv";
			 break;
		 }
		 return file;
	 }
	 
	 /**
	  * Prints the analysis menu options.
	  * @author Varunika
	  */
	 public static void printAnalysisMenu()
	 {
		 System.out.println("Which action would you like to perform?");
		 System.out.println("1. Gene Analysis Report");
		 System.out.println("2. Codon Bias Analysis");
	 }
	 
	 /**
	  * Prints the menu for codon bias analysis options.
	  * @author Varunika
	  */
	 public static void printCodonBiasMenu()
	 {
		 System.out.println("Which would you like to do?");
		 System.out.println("1. Complete Amino Acid Codon Bias Report");
		 System.out.println("2. Individual Amino Acid Codon Bias Analysis");
	 }
	 
	 /**
	  * Reads the contents of a file specified by the provided filename,
	  * concatenates the lines into a single string, and splits the string
	  * into tokens using commas as delimiters.
	  *
	  * @param RFFile - filename of the file to read
	  * @return an array of strings containing the tokens extracted from the file
	  * @throws IOException if an I/O error occurs while reading the file
	  * @author Varunika
	  */
	 public static String[] makeRFTokens(String RFFile)throws IOException
	 {
		 File file = new File(RFFile);
		 Scanner infile = new Scanner(file);
		 String str = "";
		 
		 while(infile.hasNext())
		 {
			 str += infile.nextLine();
		 }
		 infile.close();
		 String[] tokens = str.split(",");
		 
		 return tokens;
	 }
	 
	 /**
	  * Builds the amino acid table by reading data from a file of amino acid characteristics.
	  *
	  * @return an ArrayList of AminoAcid objects representing the amino acid table
	  * @throws IOException if an I/O error occurs while reading the file
	  * @author Malik and Varunika
	  */
	 public static ArrayList<AminoAcid> buildTheAATable() throws IOException
	 {
		 ArrayList<AminoAcid> listOFAA = new ArrayList<AminoAcid>();
		 File file = new File("aminoAcidTable.csv");
		 Scanner inFile = new Scanner(file);
		 // skip the first line
		 inFile.nextLine();
		 int x=0;
		 while(inFile.hasNext())
		 {
			 String str = inFile.nextLine();
			 String[] tokens = str.split(",");
			 listOFAA.add(new AminoAcid(tokens[0], tokens[2]));
			 for(int i = 3; i < tokens.length; i++)
			 {
				 /*since the amino acid instance is being made directly in the Array list it 
				 doesnt have a name so i have to access it by subscript.*/
				 listOFAA.get(x).setCodons(tokens[i]);
			 }
			 x++;
		 }
		 String[] stopCodons = {"TAG", "TAA", "TGA"};
		 listOFAA.add(new AminoAcid("Stop", "*"));
		 for(int i = 0; i < stopCodons.length; i++)
		 {
			 listOFAA.get(x).setCodons(stopCodons[i]);
		 }
		 inFile.close();
		 return listOFAA;
	 }
	 
	 /**
	  * Finds genes and their characteristics in a sequence of codons.
	  *
	  * @param codons - array of codons representing the sequence
	  * @return an ArrayList of Gene objects representing the found genes
	  * @author Varunika
	  */
	 public static ArrayList<Gene> findGenes(String[] codons)
	 {
		 ArrayList<Gene> geneSequences = new ArrayList<Gene>();
		 int startPosition = 0, stopPosition = 0;
		 for(int i=0; i<codons.length; i++)
		 {
			 if (codons[i].equals("ATG"))
			 {
				 // once a start codon is read, an array list of codons in created
				 ArrayList<String> aminoAcidSequence = new ArrayList<>();
				 startPosition = i;
				 aminoAcidSequence.add("ATG");
				 // each codon is added to the array list until a stop codon is reached
				 for(i = startPosition + 1; i<codons.length; i++)
				 {
					 aminoAcidSequence.add(codons[i]);
					 if(codons[i].equals("TAG")|| codons[i].equals("TAA")|| codons[i].equals("TGA"))
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
		 return geneSequences;
	 }
	 
	 /**
	  * Translates a gene's amino acid sequence from codons to single-letter representations.
	  *
	  * @param listOFAA - ArrayList of AminoAcid objects representing the amino acid table
	  * @param gene - Gene object representing the gene sequence
	  * @return a String representing the single-letter representation of the gene's amino acid sequence
	  * @author Varunika
	  */
	 public static String translateToSingleLetter(ArrayList<AminoAcid> listOFAA, Gene gene) {
		 ArrayList<String> aminoAcidSequence = gene.getAminoAcidSequence();
		 String singleLetterGene = "";
 
		 for (String codon : aminoAcidSequence) {
			 for (AminoAcid aminoAcid : listOFAA) {
				 if(codon.equals("TAG") || codon.equals("TAA") || codon.equals("TGA"))
				 {
					 singleLetterGene += "*";
					 break;
				 }
				 if (aminoAcid.getCodon().contains(codon)) {
					 singleLetterGene += aminoAcid.getLetterCode();
					 break; // Break once the codon is found in the amino acid's codons
				 }
			 }
		 }
 
		 return singleLetterGene;
	 }
	 
	 /**
	  * Prints the gene analysis report to the specified PrintWriter.
	  *
	  * @param singleLetterGene - single-letter representation of the gene's amino acid sequence
	  * @param listOFAA - ArrayList of AminoAcid objects representing the amino acid table
	  * @param geneSequences - ArrayList of Gene objects representing the gene sequences
	  * @param outFile - PrintWriter to which the gene analysis report will be printed
	  * @author Varunika
	  */
	 public static void printGeneAnalysis(String singleLetterGene, ArrayList<AminoAcid> listOFAA, ArrayList<Gene> geneSequences, PrintWriter outFile)
	 {
		 int i = 0;
		 for(Gene g: geneSequences)
		 {
			 singleLetterGene = translateToSingleLetter(listOFAA, geneSequences.get(i)); // Translate the first gene
			 outFile.println("Gene " + (i + 1) + " (" + g.getGeneLength() + ") :");
			 outFile.println(g.getBeginingPosition() + ".." + g.getFinalPosition());
			 outFile.println("Sequence:");
			 outFile.println(singleLetterGene);
			 outFile.println();
			 i++;
		 }
	 }
	 
	 /**
	  * Reads the given codons and analyzes their frequency in relation to the specified amino acid.
	  * Prints the codon bias report to the specified PrintWriter.
	  *
	  * @param current - AminoAcid object representing the amino acid to analyze
	  * @param codons - array of codons to be analyzed
	  * @param outFile - PrintWriter to which the codon bias report will be printed
	  * @author Malik
	  */
	 public static void codonAAReader(AminoAcid current, String[] codons, PrintWriter outFile)
	 {
		 //to print to file we made in main
		 //variable to keep count codons
		 int[] counter= new int[current.getCodonSize()];
		 int totalAA=0;
		 outFile.println("The codons for " + current.getName() + "(" + current.getLetterCode() + ") are: " + current.getCodons());
		 //this is how I read in a codon and use a nested loop to check each codon and then add to counter
		 for(int i= 0; i < codons.length; i++)
		 {
			 for(int y = 0; y < current.getCodonSize(); y++)
			 {
				 if(codons[i].equals(current.getIndividualCodon(y)))
				 {
					 counter[y]++;
					 totalAA++;
				 }
			 }
			 
		 }
		 //loop to print out the codon bias report
		 for(int z=0; z<current.getCodonSize(); z++)
		 {
		 double percent = ((double)counter[z]/totalAA)*100;
		 outFile.printf("%s: %d   %.2f%%\n", current.getIndividualCodon(z), counter[z], percent);
		 }
		 outFile.println();
	 }
	 
	 /**
	  * Searches for an AminoAcid object in the given list based on its letter code.
	  *
	  * @param listOFAA - the list of AminoAcid objects to search through
	  * @param aminoAcidCode - letter code of the amino acid to search for
	  * @return the AminoAcid object with the matching letter code, or null if not found
	  * @author
	  */
	 public static AminoAcid letterMatch(ArrayList<AminoAcid> listOFAA, String aminoAcidCode)
	 {
		 AminoAcid selectedAA = null;
		 for (AminoAcid aa : listOFAA) {
			 if (aa.getLetterCode().equalsIgnoreCase(aminoAcidCode)) {
				 selectedAA = aa;
				 break;
			 }
		 }
		 return selectedAA;
	 }
	 
	 /**
	  * Checks and prints the codon bias for the given amino acid from an array of codons.
	  * This method counts each codon that has to do with the specified amino acid and calculates
	  * a percentage of each codon equal to the total number of all codons for that amino acid. 
	  * 
	  * @param codons is an array of strings where each string represents a codon in the gene sequence.
	  * @param aminoAcid The amino acid which the codon bias analysis is going to run.
	  * @author Samuel Morales
	 */
	 public static void individualCodonBiasAnalysis(String[] codons, AminoAcid aminoAcid) {
		 int[] counts = new int[aminoAcid.getCodonSize()];
		 int total = 0;
	 
		 // Count each codon for the specified amino acid
		 for (String codon : codons) {
			 for (int i = 0; i < aminoAcid.getCodonSize(); i++) {
				 if (codon.equals(aminoAcid.getIndividualCodon(i))) {
					 counts[i]++;
					 total++;
				 }
			 }
		 }
	 
		 // Calculate and print the bias
		 System.out.println("The codons for " + aminoAcid.getName() + "(" + aminoAcid.getLetterCode() + ") are:");
		 for (int i = 0; i < aminoAcid.getCodonSize(); i++) {
			 double percentage = 100.0 * counts[i] / total;
			 System.out.printf("%s: %d   %.2f%%\n", aminoAcid.getIndividualCodon(i), counts[i], percentage);
		 }
		 System.out.println();
	 }
	 
	 /**
	  * Generates the output file name based on the action choice and reading frame.
	  *
	  * @param actionChoice - selected action (1 for gene analysis, 2 for codon bias analysis)
	  * @param readingFrame - selected reading frame (1, 2, or 3)
	  * @return the output file name
	  * @author
	  */
	 public static String outputFileName(int actionChoice, int readingFrame)
	 {
		 String outFile = "";
		 if (actionChoice == 1)
			 switch(readingFrame)
			 {
				 case 1: outFile = "covidSequenceRF1_GeneAnalysis.txt";
				 break;
				 case 2: outFile = "covidSequenceRF2_GeneAnalysis.txt";
				 break;	
				 case 3: outFile = "covidSequenceRF3_GeneAnalysis.txt";
				 break;
			 }
		 else if(actionChoice == 2)
			 switch(readingFrame)
			 {
				 case 1: outFile = "covidSequenceRF1_CodonBias.txt";
				 break;
				 case 2: outFile = "covidSequenceRF2_CodonBias.txt";
				 break;	
				 case 3: outFile = "covidSequenceRF3_CodonBias.txt";
				 break;
			 }
		 return outFile;
	 }
 }