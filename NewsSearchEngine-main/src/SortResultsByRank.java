import java.io.File;
import java.util.*;


public class SortResultsByRank {
	//Sorts values of given HashMap in descending order
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap<String,Integer> sortValuesInverse(HashMap<String,Integer> map){		
		List list = new LinkedList(map.entrySet());  
		//Custom Comparator  
		Collections.sort(list, new Comparator(){public int compare(Object o1, Object o2){return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());}});  
		//copying the sorted list in HashMap to preserve the iteration order  
		HashMap<String,Integer> sortedHashMap = new LinkedHashMap<String,Integer>();  
		for (Iterator it = list.iterator(); it.hasNext();)   
		{  
		 Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();  
		sortedHashMap.put(entry.getKey(), entry.getValue());  
		}   
		return sortedHashMap;  
	}
	//sorts values of given HashMap in ascending order
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String,Integer> sortValues(HashMap<String,Integer> map){		
		List list = new LinkedList(map.entrySet());  
		//Custom Comparator  
		Collections.sort(list, new Comparator(){public int compare(Object o1, Object o2){return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());}});  
		//copying the sorted list in HashMap to preserve the iteration order  
		HashMap<String,Integer> sortedHashMap = new LinkedHashMap<String,Integer>();  
		for (Iterator it = list.iterator(); it.hasNext();){  
			 Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();  
			sortedHashMap.put(entry.getKey(), entry.getValue());  
		}   
		return sortedHashMap;  
	}
	
	//Sorts search output in Descending Rank. ArrayList contains list of files that have the given phrase.
	public static Map<String,Integer> sortByRank(ArrayList<String> as, String phrase) {
		HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
		
		for(String fileName : as) {
			String[] words = PreSearch.getWordsFromFile(new File("WebPages/"+fileName));
			for(String word:words) {
				if(word.toLowerCase().equals(phrase.split("\\W+")[0])) {
					if(wordCount.containsKey(fileName)) {
						wordCount.put(fileName, wordCount.get(fileName)+1);					
					}
					else {
						wordCount.put(fileName, 1);
					}
				}			
			}
		}
		Map<String,Integer> sortedMap = sortValuesInverse(wordCount);
		return sortedMap;
	}
}
