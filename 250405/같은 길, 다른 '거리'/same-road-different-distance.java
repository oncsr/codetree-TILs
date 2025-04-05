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
	static List<int[]>[] V, A, B;
	static int[] DA, DB, res;
	static final int INF = (int)1e9 + 7;
	
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		M = nextInt();
		V = new List[N+1];
		A = new List[N+1];
		B = new List[N+1];
		DA = new int[N+1];
		DB = new int[N+1];
		res = new int[N+1];
		for(int i=1;i<=N;i++){
		    V[i] = new ArrayList<>();
		    A[i] = new ArrayList<>();
		    B[i] = new ArrayList<>();
		}
		for(int i=0;i<M;i++){
		    int a = nextInt(), b = nextInt(), c = nextInt(), d = nextInt();
		    A[b].add(new int[]{a,c});
		    B[b].add(new int[]{a,d});
		    V[a].add(new int[]{b,0,c,d});
		}
		
	}
	
	static void solve() throws Exception{
		
		dijk(A, DA, N);
		dijk(B, DB, N);
		for(int i=1;i<=N;i++) for(int[] e : V[i]){
		    if(DA[i] != DA[e[0]] + e[2]) e[1]++;
		    if(DB[i] != DB[e[0]] + e[3]) e[1]++;
		}
		dijk(V, res, 1);
		bw.write(res[N] + "\n");
		
	}
	
	static void dijk(List<int[]>[] G, int[] D, int start){
	    Arrays.fill(D, INF);
	    PriorityQueue<int[]> Q = new PriorityQueue<>((a,b) -> a[0]-b[0]);
	    Q.offer(new int[]{0,start});
	    D[start] = 0;
	    
	    while(!Q.isEmpty()){
	        int[] now = Q.poll();
	        int d = now[0], n = now[1];
	        if(d > D[n]) continue;
	        for(int[] e : G[n]){
	            int i = e[0], c = e[1];
	            if(D[i] > d+c){
	                D[i] = d+c;
	                Q.offer(new int[]{D[i],i});
	            }
	        }
	    }
	    
	}
	
}