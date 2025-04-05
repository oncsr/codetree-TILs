import java.util.*;
import java.io.*;

public class Main {
	
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
	static int[][] E;
	static int[] r;
	static List<int[]>[] V;
	static int mst = 0, len = 0;
	
	static int f(int x) {return x==r[x] ? x : (r[x]=f(r[x]));}
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		r = new int[N+1];
		V = new List[N+1];
		E = new int[M][3];
		for(int i=1;i<=N;i++) {
			r[i] = i;
			V[i] = new ArrayList<>();
		}
		for(int i=0;i<M;i++) for(int j=0;j<3;j++) E[i][j] = nextInt();
		
	}
	
	static void solve() throws Exception{
		
		Arrays.sort(E, (a,b) -> a[2]-b[2]);
		for(int[] e:E) {
			int a = e[0], b = e[1], c = e[2];
			int x = f(a), y = f(b);
			if(x == y) continue;
			r[x] = y;
			mst += c;
			V[a].add(new int[] {b,c});
			V[b].add(new int[] {a,c});
		}
		dfs(1,0);
		bw.write(mst + "\n" + len);
		
	}
	
	static int dfs(int n, int p) {
		int mx1 = 0, mx2 = 0;
		for(int[] next:V[n]) if(next[0] != p) {
			int i = next[0], c = next[1];
			int res = dfs(i,n) + c;
			if(res > mx1) {
				mx2 = mx1;
				mx1 = res;
			}
			else if(res > mx2) mx2 = res;
		}
		len = Math.max(len, mx1+mx2);
		return mx1;
	}
	
}