import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class assignmentP3 {

	static double log2Value = Math.log(2);
	TreeMap<Integer, docObj> docstab = new TreeMap<Integer, docObj>();
	TreeMap<String, dictObj> dicttab = new TreeMap<String, dictObj>();
	static TreeMap<String, TreeMap<Integer, Integer>> posttab = new TreeMap<String, TreeMap<Integer, Integer>>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		assignmentP3 ap3 = new assignmentP3();
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(input);
		stringOperations strop = new stringOperations();

		String inline = "";
		String[] finalin = null;

		String line1;
		String line2;
		String line4;

		BufferedReader br1 = new BufferedReader(new FileReader("docsTable.csv"));
		BufferedReader br2 = new BufferedReader(new FileReader("dictionary.csv"));
		BufferedReader br4 = new BufferedReader(new FileReader("total.txt"));

		while ((line1 = br1.readLine()) != null) {
			String[] str1 = line1.split(",");
			int id1 = Integer.parseInt(str1[0]);

			docObj doc = new docObj(0, "", "", "");
			doc.heading = str1[1];
			doc.tlength = Integer.parseInt(str1[2]);
			doc.text = str1[3];
			doc.path = str1[4];

			ap3.docstab.put(id1, doc);

		}

		while ((line2 = br2.readLine()) != null) {
			String[] str2 = line2.split(",");
			String id2 = str2[0];

			dictObj dict = new dictObj(0, 0, 0);
			dict.count = Integer.parseInt(str2[1]);
			dict.df = Integer.parseInt(str2[2]);
			dict.offset = Integer.parseInt(str2[3]);

			ap3.dicttab.put(id2, dict);

		}
		int total1 = 0;
		while ((line4 = br4.readLine()) != null) {
			total1 = Integer.parseInt(line4);
		}


		ap3.postingsFile(ap3.docstab, ap3.dicttab);
		System.out.println("Enter query : ");
		inline = in.readLine();

		while (!(inline.equals("EXIT"))) {
			PrintWriter writer = new PrintWriter(new FileWriter("result.txt", true));

			StringBuilder sb = new StringBuilder();
			Map<Integer, Double> myMap = new HashMap<Integer, Double>();
			HashMap<Integer, Integer> totaldocs = new HashMap<Integer, Integer>();

			finalin = inline.split(" ");

			ArrayList<String> temp = strop.wordProcessing(finalin);

			for (String str : temp) {

				if (ap3.posttab.get(str.trim()) != null) {
					for (Integer sr : ap3.posttab.get(str.trim()).keySet()) {
						totaldocs.put(sr, ap3.docstab.get(sr).tlength);
					}
				}
			}
			for (String str : temp) {
				double cf = 0;
				double df = 0;
				double part1 = 0;
				double part2 = 0;

				if (ap3.dicttab.containsKey(str.trim())) {
					cf = ap3.dicttab.get(str.trim()).count;
				} else {
					cf = 0;
				}

				if (ap3.dicttab.containsKey(str.trim())) {
					df = ap3.dicttab.get(str.trim()).df;
				} else {
					df = 0;
				}

				double ptotal1 = 0;
				double finalpart1 = 0;

				TreeMap<Integer, Integer> posttab2 = new TreeMap<Integer, Integer>();

			

				posttab2 = ap3.posttab.get(str.trim());

				if (posttab2 != null) {
					for (Integer sr : totaldocs.keySet()) {

						int docid = sr;
						double tf = 0.0;
						if (posttab2.containsKey(sr)) {
							tf = posttab2.get(sr);
						}

						double doclength = ap3.docstab.get(docid).tlength;

						part1 = (float) (tf / doclength);
						part1 = (float) (part1 * 0.9);

						part2 = (float) (cf / total1);
						part2 = (float) (part2 * 0.1);

						ptotal1 = part1 + part2;
						finalpart1 = Math.log(ptotal1) / log2Value;

						if (myMap.containsKey(docid)) {
							double prob = myMap.get(docid);
							prob = prob + finalpart1;
							myMap.put(docid, prob);
						}

						else {
							myMap.put(docid, finalpart1);
						}

					}
				}

			}
		
   	if(myMap.isEmpty())
			{
			  sb.append(System.getProperty("line.separator"));
				sb.append("NO RESULT");
				sb.append(System.getProperty("line.separator"));
				
			}
			int loopcount = 0;

			List<Integer> sorted = new ArrayList<Integer>(myMap.keySet());
			Map<Integer, Double> sorteddocs = new LinkedHashMap<Integer, Double>();
			Collections.sort(sorted, new Comparator<Integer>() {

				@Override
				public int compare(Integer arg0, Integer arg1) {
					// TODO Auto-generated method stub
					Double sort1 = myMap.get(arg0);
					Double sort2 = myMap.get(arg1);
					return sort2.compareTo(sort1);
				}

			});
		
			for (Integer integer : sorted) {
				sorteddocs.put(integer, myMap.get(integer));
			}
		

			Iterator it = sorteddocs.entrySet().iterator();
			loopcount = 0;
			while (it.hasNext()) {

				loopcount++;
				if (loopcount > 5)
					break;
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry) it.next();
				Integer filekey = (Integer) pair.getKey();
				double filevalue = (double) pair.getValue();

				sb.append(ap3.docstab.get(filekey).heading);
				sb.append(System.getProperty("line.separator"));
				sb.append(ap3.docstab.get(filekey).path);
				sb.append(System.getProperty("line.separator"));
				sb.append("Computed probablity : " + filevalue);
				sb.append(System.getProperty("line.separator"));
				sb.append(ap3.docstab.get(filekey).text);
				sb.append(System.getProperty("line.separator"));
				sb.append(System.getProperty("line.separator"));
			}

		
			writer.write(sb.toString());
			writer.flush();

			
			System.out.println("Enter query : ");
			inline = in.readLine();
			writer.close();
		}

	}

	void postingsFile(TreeMap<Integer, docObj> docstab, TreeMap<String, dictObj> dicttab)
			throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br3 = new BufferedReader(new FileReader("postings.csv"));
		assignmentP3 p3 = new assignmentP3();
		Set<String> keys = dicttab.keySet();
		String line3;
		for (String term : keys) {
			TreeMap<Integer, Integer> posting = new TreeMap<Integer, Integer>();
			int df = dicttab.get(term).df;
			while (df != 0 && (line3 = br3.readLine()) != null) {
				String[] str3 = line3.split(",");
				int id = Integer.parseInt(str3[0]);
				int tf = Integer.parseInt(str3[1]);
				posting.put(id, tf);
				df--;
			}
			p3.posttab.put(term, posting);
		}

	}

}
