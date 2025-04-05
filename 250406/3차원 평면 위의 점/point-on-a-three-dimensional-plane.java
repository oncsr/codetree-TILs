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
	
	static int N;
	static int[][] A;
	static int[] r;
	
	static int f(int x) {return x==r[x] ? x : (r[x]=f(r[x]));}
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		r = new int[N];
		A = new int[N][4];
		for(int i=0;i<N;i++) {
			for(int j=0;j<3;j++) A[i][j] = nextInt();
			A[i][3] = i;
			r[i] = i;
		}
		
	}
	
	static void solve() throws Exception{
		
		List<int[]> E = new ArrayList<>();
		for(int k=0;k<3;k++) {
			final int j = k;
			Arrays.sort(A, (a,b) -> a[j]-b[j]);
			for(int i=0;i<N-1;i++) E.add(new int[] {A[i+1][j] - A[i][j], A[i+1][3], A[i][3]});
		}
		Collections.sort(E, (a,b) -> a[0]-b[0]);
		
		long ans = 0;
		for(int[] e : E) {
			int a = e[1], b = e[2], c = e[0];
			int x = f(a), y = f(b);
			if(x == y) continue;
			r[x] = y;
			ans += c;
		}
		bw.write(ans + "\n");
		
	}
	
}