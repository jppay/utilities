
import org.apache.commons.lang.StringUtils;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;



/**
 * An advanced score that takes initials and middle names into account when 
 * calculating a score.
 * @author jppay
 */
public class LevenshteinAbbrScoring extends AbstractScoring {
	

	
	/**
	 * The score that is given to an initial when a word is found that starts 
	 * with that initial.
	 */
	private static final float ABBREVIATION_SCORE = 0.8f;
	
	/**
	 * The score that is given to a missing word when there is exactly one 
	 * word missing.
	 */
	private static final float SINGLE_MISSING_WORD_SCORE = 0.8f;
	
	/**
	 * The score that is given to a missing word when there are multiple words missing.
	 */
	private static final float MULTIPLE_MISSING_WORD_SCORE = 0.6f;
	
	/**
	 * The score that is given to a word that is missing when there are more than 
	 * <code>MANY_MISSING_WORDS</code> missing.
	 */	
	private static final float MANY_MISSING_WORD_SCORE = 0.4f;
	
	/**
	 * Indicates the number of missing words before the difference is considered great
	 */
	private static final int   MANY_MISSING_WORDS = 4;
	
	/**
	 * The score that is given to an initial when no matching word is found
	 */
//	private static final float NO_INITIAL_MATCH_SCORE = 0.6f;
	
	/**
	 * The maximum number of characters in a word that is considered as a short word 
	 */
	private static final int   SHORT_WORD = 4;
	
	/**
	 * The maximum number of characters that a short word may differ from another word 
	 * in order to be included in a score.
	 */
	private static final int   MAX_DIFFERENCE_SHORT_WORD = 1;


	public LevenshteinAbbrScoring() {
	}
	
	public boolean isCompatible(String[] text1, String[] text2, float minimumScore) {
		return getScore(text1, text2) >= minimumScore;
	}
	
	
	public float getScore(String[] textInput1, String[] textInput2) {
		
		final String[] text1;
		final String[] text2;

		/*
		 * Make text1 the shortest one
		 */		
		if (textInput1.length < textInput2.length) {
			text1 = textInput1;
			text2 = textInput2;
		} else {
			text1 = textInput2;
			text2 = textInput1;
		}
		

		/*
		 * The total score between text1 and text2.
		 */
		float score = 0.0f;
		
		/*
		 * The current word index in text1.
		 */
		int index1 = 0;
		
		/*
		 * The number of times a word is encountered that has a matching word in text2.
		 */
		int nbMatchingWords = 0;		
		
		/*
		 * Indicates if at least one full word matches (and not only initials)
		 */
		boolean hasFullWordMatch = false;
		
		/*
		 * Indicates which words of text1 have a match
		 */
		boolean[] text1Match = new boolean[text1.length];

		/*
		 * Indicates which words of text2 have a match
		 */
		boolean[] text2Match = new boolean[text2.length];	


		/*
		 * First iteration : retrieve all exact match combinations  
		 */
		for (index1 = 0; index1 < text1.length; index1++)
		{
			int tempDistance = -1;
			int index2 = 0;
			int bestIndex = -1;

			// If a tempDistance == 0 is found, then there is no need to process further entries
			while (index2 < text2.length && tempDistance != 0)
			{
				// Only calculate the distance if there's isn't another word yet that matches with the current word
				if (!text2Match[index2])
				{
					if(text1[index1].equals(text2[index2]))
					{
//						log.debug("Exact match between ("+text1[index1]+" vs "+text2[index2]+"): ");
						tempDistance = 0;
						bestIndex = index2;
					}
				}

				index2++;
			}

			if (tempDistance != -1)
			{
				score += 1;
				if(text1[index1].length() > 1)
					hasFullWordMatch = true;
				text2Match[bestIndex] = true;
				text1Match[index1] = true;
			}
		}
		
		/*
		 * Second iteration : Calculate distance of all other full word combinations    
		 */
		
		for (index1 = 0; index1 < text1.length; index1++)
		{
			if (!text1Match[index1])
			{
				int index2 = 0;
				int bestIndex = -1;
				
				String current = text1[index1];

				/*
				 * The current word is a single lettter.
				 */						
				if (current.length() == 1) {
					float tempScore = 0.0f;				
					while (index2 < text2.length && tempScore < 1) {
					
						if (!text2Match[index2]) {
					
							if (current.charAt(0) == text2[index2].charAt(0)) {
								if (text2[index2].length() == 1) {
									tempScore = 1.0f;
									bestIndex = index2;
								} else {
									if (tempScore < ABBREVIATION_SCORE) {
										tempScore = ABBREVIATION_SCORE;
										bestIndex = index2;
									}
								}
							}
						}
						index2++;
					}
					if (tempScore != 0.0f) {
						text2Match[bestIndex] = true;
						text1Match[index1] = true;
						score += tempScore;					
					}
				}
			
				/*
				 * The current word is more than one character long.
				 */
				else
				{
					float currentScore = 0.0f;
					while (index2 < text2.length) {
					
						if (!text2Match[index2]) {
					
							if (text2[index2].length() == 1) {
								if (current.charAt(0) == text2[index2].charAt(0)) {
									if (currentScore < ABBREVIATION_SCORE) {							
										currentScore = ABBREVIATION_SCORE;
										bestIndex = index2;
									}
								}
							} else {
								/*
								 * When the difference in length of the two words is great, then don't even bother 
								 * to calculate the Levenshtein distance. 
								 */
								if (Math.abs(text1[index1].length() - text2[index2].length()) <= ScorerHelper.MAX_DIFFERENT_LETTERS) {						 
									/*
									 * The distance between the current word and the current word in text2.
									 */
									int newDistance = StringUtils.getLevenshteinDistance(current,text2[index2]);
								
									/*
									 * Penalty if the first letter differs
									 */								
									if(current.charAt(0)!=text2[index2].charAt(0))
										newDistance += 1;
								
									/*
									 * If the current word is short, the word must differ in less than MAX_DIFFERENCE_SHORT_WORD 
									 * characters in order to be included in the result.
									 */
									if (current.length() <= SHORT_WORD) {
										if (newDistance <= MAX_DIFFERENCE_SHORT_WORD) {
											float newScore = ScorerHelper.convertDistanceToScore(newDistance, current.length(), text2[index2].length());
											if (newScore > currentScore) {										
												currentScore = newScore;
												bestIndex = index2;
												hasFullWordMatch = true;
											}
										}
									
									/*
									 * If the current word is long, the word must differ in less than MAX_DIFFERENCE_LONG_WORD 
									 * characters in order to be included in the result.
									 */							
									} else {
										if (newDistance <= current.length()/2 + 1) {
											float newScore = ScorerHelper.convertDistanceToScore(newDistance, current.length(), text2[index2].length());
											if (newScore > currentScore) {
												currentScore = newScore;
												bestIndex = index2;
												hasFullWordMatch = true;
											}
										}
									}
								}
							}
						
						}
						index2++;
					}
//					log.debug("Adding word score "+ tempScore);
					if (currentScore > 0.0f) {
						score += currentScore;
						text2Match[bestIndex] = true;
						text1Match[index1] = true;						
					}
				}
			}
		}	
		
		/*
		 * Third iteration : Score calculation    
		 */
		
		//There is not a single word that matches (eg.: all matches are for initials)
		if (!hasFullWordMatch) {
			return 0.0f;
		}
		
		int NbNonMatchingInput1 = 0;
		for (int i = 0; i < text1Match.length; i++)
		{
			if (!text1Match[i]) 
			{
				NbNonMatchingInput1 ++;
			} else {
				nbMatchingWords ++;
			}
		}
		
		int NbNonMatchingInput2 = 0;
		for (int i = 0; i < text2Match.length; i++)
		{
			if (!text2Match[i])
			{
				NbNonMatchingInput2 ++;
			}
		}		
		
		
		// score for words that do not match 
		score += getDifferenceScore(NbNonMatchingInput1 + NbNonMatchingInput2);		
//		log.debug("Number of non matching words: " + (NbNonMatchingInput1 + NbNonMatchingInput2) + ", Score for non matching words: " + getDifferenceScore(NbNonMatchingInput1 + NbNonMatchingInput2));

		//The number of items (words) that will be taken into account when calculating the score.
		int nbItems = nbMatchingWords + NbNonMatchingInput1 + NbNonMatchingInput2;

		
		//Extra penalty if there are non matching words in both input and matched data
		if(NbNonMatchingInput1*NbNonMatchingInput2 > 0){
			nbItems += 1;
//			log.debug("Extra penalty because both nonmatching words in input and result");
		}

//		log.debug("nbItems: " + nbItems);
//		log.debug("nbMatchingWords: " + nbMatchingWords);
//		log.debug("Total score: "+score);
		
		score /= nbItems;
		
//		log.debug("NbItems: "+nbItems);	
//		log.debug("Score: "+score);
		
		return score;	

	}

	/**
	 * Returns a score based on the number of different words.
	 * @param nbDifferentWords
	 * @return
	 */
	private float getDifferenceScore(int nbDifferentWords) {
		float differenceScore = 0.0f;	
		if (nbDifferentWords == 1) {
			differenceScore = SINGLE_MISSING_WORD_SCORE;
		} else if (nbDifferentWords >= MANY_MISSING_WORDS) {
			differenceScore = nbDifferentWords*MANY_MISSING_WORD_SCORE;
		} else {
			differenceScore = nbDifferentWords*MULTIPLE_MISSING_WORD_SCORE;
		}	
		return differenceScore;
	}

}
