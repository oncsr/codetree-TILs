import java.util.*;
import java.io.*;

class Query{
	String op;
	List<String> params;
	int ans = 0;
	Query(String op, List<String> params){
		this.op = op;
		this.params = params;
	}
}

class Element{
	String name;
	long rvalue;
	int value = 0;
	Element(String name, long rvalue){
		this.name = name;
		this.rvalue = rvalue;
	}
}

class SegTree{
	int size;
	long[] seg;
	int[] cnt;
	String[] names;
	SegTree(int size){
		this.size = size;
	}
	void init() {
		seg = new long[size * 4];
		cnt = new int[size * 4];
		names = new String[size+1];
	}
	long upt(int s, int e, int i, long v, String name, int n) {
		if(s == e) {
			if(v > 0) {
				if(seg[n] != 0) return 0;
			}
			else {
				if(seg[n] == 0) return 0;
			}
			long prev = seg[n];
			seg[n] = v;
			cnt[n] = v > 0 ? 1 : 0;
			names[s] = name;
			return prev;
		}
		int m=(s+e)>>1;
		long res;
		if(i <= m) res = upt(s,m,i,v,name,n*2);
		else res = upt(m+1,e,i,v,name,n*2+1);
		seg[n] = seg[n*2] + seg[n*2+1];
		cnt[n] = cnt[n*2] + cnt[n*2+1];
		return res;
	}
	String rank(int s, int e, int k, int n) {
		if(s == e) return names[s];
		int m=(s+e)>>1;
		if(k <= cnt[n*2]) return rank(s,m,k,n*2);
		return rank(m+1,e,k-cnt[n*2],n*2+1);
	}
	long sum(int s, int e, int l, int r, int n) {
		if(l>r || l>e || r<s) return 0;
		if(l<=s && e<=r) return seg[n];
		int m=(s+e)>>1;
		return sum(s,m,l,r,n*2) + sum(m+1,e,l,r,n*2+1);
	}
}

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	static Query[] querys;
	static int Q;
	static TreeMap<String, Integer> map = new TreeMap<>();
	static List<Element> temp = new ArrayList<>();
	
	static TreeSet<String> existNames = new TreeSet<>();
	static TreeSet<Integer> existValues = new TreeSet<>();
	
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
	}
	
	static void ready() throws Exception{

		input();
		
		coordinateCompression();
		
	}
	
	static void input() throws Exception {
		
		Q = Integer.parseInt(br.readLine());
		querys = new Query[Q];
		for(int i=0;i<Q;i++) {
			nextLine();
			String op = st.nextToken();
			List<String> params = new ArrayList<>();
			
			
			if(op.equals("insert")) {
				String name = st.nextToken();
				String value = st.nextToken();
				params.add(name);
				params.add(value);
				querys[i] = new Query(op,params);
				temp.add(new Element(name, Long.parseLong(value)));
				
				continue;
			}
			
			while(st.hasMoreTokens()) params.add(st.nextToken());
			querys[i] = new Query(op,params);
			
		}
		
	}
	
	static void coordinateCompression() {
		
		Collections.sort(temp, (a,b) -> {
			if(a.rvalue == b.rvalue) return 0;
			if(a.rvalue < b.rvalue) return -1;
			return 1;
		});
		
		int compressedValue = 1;
		for(Element e : temp) {
			e.value = compressedValue;
			map.put(e.name, e.value);
			compressedValue++;
		}
		
	}
	
	static void solve() throws Exception{
		
		int N = temp.size() + 1;
		SegTree segtree = new SegTree(N);
		
		for(Query q : querys) {
			String op = q.op;
			if(op.equals("init")) {
				segtree.init();
				existNames = new TreeSet<>();
				existValues = new TreeSet<>();
			}
			else if(op.equals("insert")) {
				String name = q.params.get(0);
				long value = Long.parseLong(q.params.get(1));
				
				if(existNames.contains(name) || existValues.contains((int)value)) {
					bw.write("0\n");
				}
				else {
					existNames.add(name);
					existValues.add((int)value);
					int idx = findIndexToValue((int)value) + 1;
					long res = segtree.upt(1, N, idx, value, name, 1);
					
					if(res == 0) bw.write("1\n");
					else bw.write("0\n");
					
				}
				
			}
			else if(op.equals("delete")) {
				String name = q.params.get(0);
				
				int idx = findIndexToName(name) + 1;
				if(idx == 0) bw.write("0\n");
				else {
					long res = segtree.upt(1, N, idx, 0, "", 1);
					
					if(res == 0) bw.write("0\n");
					else {
						existNames.remove(name);
						existValues.remove((int)res);
						bw.write(res + "\n");
					}
					
				}
			}
			else if(op.equals("rank")) {
				long value = Long.parseLong(q.params.get(0));
				
				if(value > segtree.cnt[1]) bw.write("None\n");
				else bw.write(segtree.rank(1, N, (int)value, 1) + "\n");
				
			}
			else {
				long value = Long.parseLong(q.params.get(0));
				
				bw.write(segtree.sum(1, N, 1, findIndexToValue((int)value) + 1, 1) + "\n");
			}
			
			bw.flush();
		}
		
	}
	
	static int findIndexToValue(int k) {
		int s = 0, e = temp.size()-1, m = (s+e+1)>>1;
		while(s<e) {
			if(temp.get(m).rvalue > k) e = m-1;
			else s = m;
			m = (s+e+1)>>1;
		}
		return m;
	}
	
	static int findIndexToName(String name) {
		return map.getOrDefault(name, -1);
	}
	
}