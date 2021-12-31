import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Uses Jsoup to crawl through web and creates text files with parsed information.
public class WebCrawler {
	public static String crawl(String link) {
		
		
		String html = urlToHTML(link);
		
		Document doc = Jsoup.parse(html);
		String text = doc.text();
		String title = doc.title();
		//System.out.print(text);
		createFile(title,text,link);
		
		Elements e = doc.select("a");
		String links = "";
		
		for(Element e2 : e) {
			String href = e2.attr("abs:href");
			if(href.length()>3)
			{
				links = links+"\n"+href;
			}
		}
		return links;
	}
	public static void createFile(String title,String text,String link) {
		try {
			String[] titlesplit = title.split("\\|");
			String newTitle = "";
			for(String s : titlesplit) {
				newTitle = newTitle+" "+s;
			}
			File f = new File("WebPages//"+newTitle+".txt");
			f.createNewFile();			
			PrintWriter pw = new PrintWriter(f);
			pw.println(link);
			pw.println(text);
			pw.close();
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public static String urlToHTML(String link){
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			Scanner sc = new Scanner(conn.getInputStream());
			StringBuffer sb = new StringBuffer();
			while(sc.hasNext()) {
				sb.append(" "+sc.next());
			}
			
			String result = sb.toString();
			sc.close();
			return result;
		}
		catch(IOException e) {System.out.println(e);} 
		return link;
	}
	
	public static void crawlPages(String links) {
		
		try {
			File f = new File("CrawledPages.txt");
			f.createNewFile();
			FileWriter fwt = new FileWriter(f);
			fwt.close();
						
			String links2 = "";
			for(String link: links.split("\n")) {				
				links2 = links2 + crawl(link);
				System.out.println(link);				
				FileWriter fw = new FileWriter(f,true);
				fw.write(link + "\n");
				fw.close();
			}
			
			String links3 = "";
			for(String link: links2.split("\n")) {
				In in = new In(f);
				String linksRead = in.readAll();
				if(!linksRead.contains(link)) {
					links3 = links3 + crawl(link);
					System.out.println(link);
					FileWriter fw = new FileWriter(f,true);
					fw.write(link + "\n");
					fw.close();
				}
				//System.out.println(link);				
				
			}
			
			for(String link: links3.split("\n")) {
				In in = new In(f);
				String linksRead = in.readAll();
				if(!linksRead.contains(link)) {
					crawl(link);
					System.out.println(link);
					FileWriter fw = new FileWriter(f,true);
					fw.write(link + "\n");
					fw.close();
				}
				//System.out.println(link);				
				
			}
		
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public static void crawlDefault() {
		String links="https://www.cbc.ca/"+"\n"+"https://www.bbc.com/news/world/us_and_canada"+"\n"+"https://www.ctvnews.ca/"+"\n"+"https://www.cicnews.com/";
		crawlPages(links);
	}
	public static void crawlCustom(String line) {
		String[] links = line.split(" ");
		String newLine = "";
		for(String link : links) {
			newLine = newLine + link + "\n";
		}
		crawlPages(newLine);
	}
	
	public static void wipeWebPages() {
		File directory = new File("WebPages");
		File files[] = directory.listFiles();
		for (File f : files) {
			f.delete();
		}
		System.out.println("WebPages wiped!");
	}
	
	public static void main(String[] args) {
		crawlDefault();
	}
}
