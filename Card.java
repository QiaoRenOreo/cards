package ss.week6.cards;
import java.io.*;
public class Card 
{

	// ---- constants -----------------------------------

	// ranks are 2, ..., 9 and:
	public static final char JACK = 'J';
	public static final char QUEEN = 'Q';
	public static final char KING = 'K';
	public static final char ACE = 'A';
	public static final char TEN = 'T';
	
	// suits are:
	public static final char CLUBS = 'C';
	public static final char DIAMONDS = 'D';
	public static final char HEARTS = 'H';
	public static final char SPADES = 'S';

	// some convenient arrays
	private static final char[] RANK_CHARACTERS = "23456789TJQKA".toCharArray();
	private static final char[] SUIT_CHARACTERS = {'C', 'D', 'H', 'S'};
	private static final String[] RANK_STRINGS = {"2", "3", "4", "5", "6", "7",
	    "8", "9", "10", "jack", "queen", "king", "ace"};//ace:尖儿1
	private static final String[] SUIT_STRINGS = {"Clubs", "Diamonds",
	    "Hearts", "Spades"};//梅花，方块，心，桃子

	// ---- class methods -------------------------------------

	/**
	 * Translates a char encoding of rank into it's String representation.
	 * @return the String representation of rank
	 * @param  rank the char encoding a rank
	 * @return null if <code>isValidRank(rank)</code> returns <code>false</code>
	 */
	private static String rankChar2String(char rank) 
	{
		int i;
		for (i = 0; i < 13 && RANK_CHARACTERS[i] != rank; i++)
			;
		return (i == 13) ? null : RANK_STRINGS[i]; 
	}

	/**
	 * Translates a suit encoding of rank into its String representation.
	 * @return the String representation of suit
	 * @param  rank the char encoding a suit
	 * @return null if <code>isValidSuit(suit)</code> returns <code>false</code>
	 */
	private static String suitChar2String(char suit) 
	{
		int i;
		for (i = 0; i < 4 && SUIT_CHARACTERS[i] != suit; i++)
			;
		return (i == 4) ? null : SUIT_STRINGS[i];
	}

	/**
	 * Translates a String encoding of rank into its character representation.
	 * @param  rank the String encoding a rank
	 * @return the char encoding of rank
	 * @return '?' if <code>isValidRank(rank)</code> returns <code>false</code>
	 */
	private static char rankString2Char(String rank) {
		int i;
		for (i = 0; i < 13 && !(RANK_STRINGS[i].equals(rank)); i++)
			;
		return (i == 13) ? '?' : RANK_CHARACTERS[i];
	}

	/**
	 * Translates a suit String into its character encoding.
	 * @param  rank the String representation of a suit
	 * @return the character encoding of suit
	 * @return '?' if <code>isValidSuit(suit)</code> returns <code>false</code>
	 */
	private static char suitString2Char(String suit) {
		int i;
		for (i = 0; i < 4 && !(SUIT_STRINGS[i].equals(suit)); i++)
			;
		return (i == 4) ? '?' : SUIT_CHARACTERS[i];
	}

	/**
	 * Tests if a <tt>char</tt> represents a valid suit.
	 * @return <tt>true</tt> if 
	 *         <tt>k</tt> in <tt>CLUBS | DIAMONDS | HEARTS | SPADES</tt>
	 */
	/*@pure*/
	public static boolean isValidSuit(char suit) {
		return suit == CLUBS || suit == DIAMONDS || suit == HEARTS
				|| suit == SPADES;
	}

	/**
	 * Tests if a <tt>char</tt> represents a valid rank.
	 * @return <tt>true</tt> if 
	 * <tt>k</tt> in <tt>'2'..'9' | TEN | JACK | QUEEN | KING | ACE</tt>
	 */
    /*@pure*/
	public static boolean isValidRank(char r) {
		return ('2' <= r && r <= '9') || r == TEN || r == JACK || r == QUEEN
				|| r == KING || r == ACE;
	}

	/*@
	   requires isValidSuit(s1) && isValidSuit(s2);
	 */
	/**
	 * Tests if a suit is value of a suit is less than the value of
	 * another suit following the order
	 * CLUBS梅花 < DIAMONDS < HEARTS < SPADES桃.
	 */
	public static boolean suitLessThan(char s1, char s2) {
		boolean result = false;
		if (s1 == CLUBS) {
			result = s2 != CLUBS;
		} else if (s1 == DIAMONDS) {
			result = s2 != CLUBS && s2 != DIAMONDS;
		} else if (s1 == HEARTS) {
			result = s2 == SPADES;
		}
		return result;
	}

    /*@
       requires isValidRank(r1) && isValidRank(r2);
     */
	/**
	 * Tests if one rank is less then the other following the order
	 * '2' < '3' < ... < TEN < JACK < QUEEN < KING < ACE.
	 */
	public static boolean rankLessThan(char r1, char r2) {
		int i;
		for (i = 0; RANK_CHARACTERS[i] != r1 && RANK_CHARACTERS[i] != r2; i++)
			;
		return RANK_CHARACTERS[i] == r2 ? false : RANK_CHARACTERS[i] == r1;
	}

	/*@
       requires isValidRank(r1) && isValidRank(r2);
     */
	/**
	 * Tests if one rank directly follows the other accroding to
	 * '2' < '3' < ... < TEN < JACK < QUEEN < KING < ACE.
	 */
	public static boolean isRankFollowing(char r1, char r2) {
		boolean result = false;
		if (r1 == KING) {
			result = r2 == ACE;
		} else if (r1 == QUEEN) {
			result = r2 == KING;
		} else if (r1 == JACK) {
			result = r2 == QUEEN;
		} else if (r1 == TEN) {
			result = r2 == JACK;
		} else if (r1 == '9') {
			result = r2 == TEN;
		} else {
			result = r2 == r1 + 1;
		}
		return result;
	}
	
	// ---- instance variabeles -----------------------------------

	/*@
	   private invariant isValidSuit(suit);
	 */
	/**
	 * Suit of this card.
	 */
	private static char suit;
	/*@
	   private invariant isValidRank(rank);
	 */
	/**
	 * Rank of this card..
	 */
	private static char rank;

	// ---- constructors -----------------------------------

	/*@
	   requires isValidSuit(s) && isValidRank(r);
	 */
	/** 
	 * Creates a new Card with the given suit and rank.
	 * @param   s suit of the new card.
	 * @param   r rank of the new card.
	 */
	public Card(char s, char r) {
		assert isValidSuit(s) && isValidRank(r);
		suit = s;
		rank = r;
	}

	// ---- queries ---------------------------------------

	/*@
	   ensures isValidSuit(\result);
	 */
	/**
	 * Returns the suit of this card. 
	 * @return suit of this card.
	 */
	public char getSuit() {
		return suit;
	}

    /*@
       ensures isValidRank(\result);
    */
	/** 
	 * Returns the rank of this card. 
	 * @return rank of this card.
	 */
	public char getRank() {
		return rank;
	}

	/** Returns a String description of this card. */
	public String toString() {
		String res;

		switch (getSuit()) {
    		case CLUBS:
    		    res = "Clubs";
    		    break;
    		case DIAMONDS:
    		    res = "Diamonds";
    		    break;
    		case HEARTS:
    		    res = "Hearts";
    		    break;
    		default:
    		    res = "Spades";
		}
		res += " ";
		switch (getRank()) {
    		case TEN:
    		    res += "10";
    		    break;
    		case JACK:
    		    res += "jack";
    		    break;
    		case QUEEN:
    		    res += "queen";
    		    break;
    		case KING:
    		    res += "king";
    		    break;
    		case ACE:
    		    res += "ace";
    		    break;
    		default:
    		    res += getRank();
		}
		return res;
	}

	/**
	 * Tests if this card is equal to another (i.e., same suit and rank).
	 * @param  obj Card to be compared.
	 * @return <code>true</code> if the suit and rank of <code>obj</code> 
	 *         are the same as of this Card.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Card)) {
			return false;
		}
		Card card = (Card) obj;
		return this.getSuit() == card.getSuit()
				&& this.getRank() == card.getRank();
	}

	/**
	 * Overwrites the hashcode from Object.
	 */
	public int hashCode() {
		return 100 * rank + suit; 
	}

	/*@
	   requires card != null;
	 */
	/**
	 * Tests if this Card is less in suit than another Card.
	 * @see #suitLessThan(char, char)
	 * @param   card Card for the comparison.
	 * @return  <code>true</code> if the suit of this Card is less than that of <code>card</code>.
	 */
	public boolean ltSuit(Card card) {
		return suitLessThan(this.getSuit(), card.getSuit());
	}

    /*@
       requires card != null;
     */
	/**
	 * Tests if this Card is less in rank that another Card.
	 * @see #rankLessThan(char, char)
	 * @param   card Card for the comparison
	 * @return  <code>true</code> if the rank of this Card is less than that of <code>kaart</code>.
	 */
	public boolean ltRank(Card card) {
		return rankLessThan(this.getRank(), card.getRank());
	}

    /*@
       requires card != null;
     */
	/**
	 * Tests if this Card is directly followed in rank by another Card.
	 * Does not consider suit.
	 * see {@link #isRankFollowing(char, char)}
	 * @param   card Card for the comparison.
	 * @return  <code>true</code> if the rank of this Card directly precedes the rank of <code>card</code>.
	 */
	public boolean isInRankBefore(Card card) {
		return isRankFollowing(this.getRank(), card.getRank());
	}

	//--------------------------------------------------
	/**P6.4 Add a method write to the class Card. 
	 * It should have a PrintWriter as parameter, 
	 * and the result of the method should be that :
	 * the object on which the method is called sends a description of itself (obtained
by the use of toString) to the PrintWriter.*/
	public void write (PrintWriter printWriter)
	{
		printWriter.println(this.toString());
	}
	
	/**P6.4, 
	 * Creating the PrintWriter object should be done using a PrintStream (in this case System.out). 
	 * create a main-method which creates several Card instances and writes to these files. 
	 * This main method should use the standard output (System.out) in case no file name is given as an argument.
	 * @throws IOException */
/*	public static void main (String[] args) throws IOException
	{
		//user gives args[], such as java ss.week6.card.Card cardfile.txt
		if (args!=null)
		{ 
			try {
				FileWriter writer = new FileWriter(args[0]); 
				// "d:\2015 Qiao\UT ATLAS study\semester 6\softwaresystem\softwaresystems\src\ss\week6\"+
				PrintWriter printWriter = new PrintWriter(writer);
				Card card1= new Card('C', '2');
				Card card2= new Card('D', '4');
				Card card3= new Card('H', '6');
				Card card4= new Card('S', 'Q');
				card1.write(printWriter);
				card2.write(printWriter);
				card3.write(printWriter);
				card4.write(printWriter);
				printWriter.close();
			}catch(IOException e)
			{
				System.out.println("wrong");
			}
		}
		else 
		{
			System.out.println (" no file is created");
		}
	}*/

	
	//P6.5 
	/**The method should read from BufferedReader in 
	 * and returns a Card instance on the basis of this input.
Make sure that:
• read returns null if the BufferedReader does not allow you to construct a valid card, 
for example because the line that was read does not correspond to the format “suit rank”.
• read returns a EOFException when the BufferedReader is finished.*/
	public static Card read ( BufferedReader in) throws EOFException
	{
		char suitChar;
		char rankChar;
		String line;
		try 
		{
			line=in.readLine();
		}catch (IOException e){
			return null;
		}

		if (line != null) 
		{
			if (line.contains(" "))
			{
				String[] parts =line.split(" ");
				String suit=parts[0];
				String rank=parts[1];
				suitChar=suitString2Char(suit);
				rankChar=rankString2Char(rank);
				if (isValidSuit(suitChar) && isValidRank(rankChar))
				{
					Card newCard =new Card(suitChar,rankChar);
					return newCard;
				}
			}
		}
		else
		{
			EOFException e=new EOFException("end of file");
			throw e;
		}
		return null;
	}
	//P6.6 test
	/**
	 * P-6.7 Implement methods read(DataInput in) and write(DataOutput out) in class Card. 
	 * Make sure that they match: anything that is written by write should be readable by read. 
	 * If anything goes wrong while reading, 
	 * read should treat exceptions in the same way as read(BufferedReader in) in Exercise P-6.5.
Notice that a Card is uniquely represented by its rank and suit. 
Both can be represented by a char.
Therefore, Card should be stored as two char values and not as String.
Use ss.week6.test.CardTest and CardReader to test your implementations.*/
	public static Card read(DataInput in) throws IOException
	{
		char suitChar;
		char rankChar;
		try {
        suitChar = in.readChar();
        rankChar = in.readChar();
		if (isValidSuit(suitChar) && isValidRank(rankChar))
		{
			Card newCard =new Card(suitChar,rankChar);
			return newCard;
		}
		}catch (IOException e){
			throw e;
		}

		return null;
	}
	
	/**If anything goes wrong while writing, write should throw a IOException*/
	public void write(DataOutput out) throws IOException
	{
		if (out!=null)
		{
			out.writeChars(this.toString());//write string
		}
		else
		{
			IOException e=new IOException("somthing goes wrong");
			throw e;
		}
	}
	//P 6.8
	/**Extend the class Card with methods write(ObjectOutput) and read(ObjectInput), 
	 * similarly to the data-based methods in Exercises P-6.5 and P-6.4.
As always, it should be possible to write a Card object and read it back in again.
not all objects can be handled by object streams, since the objects class should be serializable*/
	public void write(ObjectOutput out) throws IOException
	{
        try
        {
        	out.writeObject(this); //write this class. handle IOException
        }
        catch (IOException e)
        {
        	throw e;
        }
	}
	
	public static Card read(ObjectInput in) throws IOException, ClassNotFoundException
	{
		if (in!=null)
		{
			try
			{
				Card untestedCard = (Card) in.readObject();
				if (isValidSuit(untestedCard.suit) && isValidRank(untestedCard.rank))
				{
					Card newCard =new Card(untestedCard.suit,untestedCard.rank);
					return newCard;
				}
				
			}catch (ClassNotFoundException e){
				throw e;
			}catch (IOException e){
				throw e;
			}
		}
		return null;
	}
	public static void main (String[] args) throws IOException
	{
		Card card1= new Card('C', '2');
		
		DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("D:\\dataChannel.txt"));
		card1.write(dataOut);
		
		ObjectOutputStream objectOut=new ObjectOutputStream (new FileOutputStream ("D:\\objectChannel.txt"));
		card1.write(objectOut);	

        PrintWriter pw = new PrintWriter(new FileOutputStream ("D:\\pw.txt"));
        card1.write(pw);	
	
	}
}