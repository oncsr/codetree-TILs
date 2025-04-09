import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		while(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static double nextDouble() throws Exception { return Double.parseDouble(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static int N, M;
	static List<Integer>[] V;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		V = new List[N+1];
		for(int i=1;i<=N;i++) V[i] = new ArrayList<>();
		while(M-->0) {
			int a = nextInt(), b = nextInt();
			V[a].add(b);
			V[b].add(a);
		}
		
	}
	
	static void solve() throws Exception{
		
		Queue<int[]> Q = new LinkedList<>();
		Q.offer(new int[] {1,0});
		int[] C = new int[N+1];
		Arrays.fill(C, -1);
		C[1] = 0;
		
		while(!Q.isEmpty()) {
			int[] now = Q.poll();
			int n = now[0], t = now[1];
			for(int i:V[n]) if(C[i] == -1) {
				C[i] = t^1;
				Q.offer(new int[] {i,t^1});
			} else if(C[i] != (t^1)) {
				bw.write("0");
				return;
			}
		}
		bw.write("1");
		
	}
	
}