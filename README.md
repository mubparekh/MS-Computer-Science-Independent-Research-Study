# MS-Computer-Science-Independent-Research-Study

Project Topic-
Multiple Protein Network Alignment Using Pair-wise IBNAL Aligner 

Abstract- 
Protein-protein interaction and their networks solve the problem of ﬁnding biological correspondences between species through their PPI network alignment. IBNAL is a cliquebased index network aligner which ﬁnds conserved network patterns accross pairs of networks. We present a global network alignment of multiple protein networks using IBNAL pair-wise aligner. Our algorithm presents four novel multiple alignment ways based on the number of cliques and nodes present in the networks to be aligned- Ascending and Descending sequence alignment, Consecutive network alignment and High-low network alignment. Our results conﬁrm that ascending alignment of the networks which are aligned from highest number of cliques to lowest number of cliques produces a better aligner than other alignment ways. Using our results we also show that as the number of alignment pair increases the quality of resulting alignment decreases. Index Terms—cliques, subordinate nodes, multiple network alignment, pair-wise network alignment
// For more information please refer the research paper.

Java Algorithm-
Extension to IBNAL aligner
//Pseudocode can be refered from the research paper
Aligns multiple protein networks (undirected graphs) in four different ways. 
1) Ascending sequence multiple alignment: Networks are first arranged in ascending order based on the number of cliques present in each network and then aligned in sequence to obtain final alignment.
2) Descending sequence multiple alignment: Networks are first arranged in descending order based on the number of cliques present in each network and then aligned in sequence to obtain final alignment.
3) Consecutive pair multiple alignment: First, networks are arranged in descending order based on the number of cliques present and then all consecutive pairs are aligned. Second, the results obtained are arranged based on the subordinate nodes present in each network and then final sequencial alignment to obtain final alignment.
4) High-low pair alignment: First, networks are arranged in descending order based on the number of cliques present and then the pairs that has highest cliques is aligned with the one (lowest cliques). Second, the results obtained are arranged based on the subordinate nodes present in each network and then final sequencial alignment to obtain final alignment. 
