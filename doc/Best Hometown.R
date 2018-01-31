setwd("C:/workspace/heuristic/BestHomeTown")

cities = c("Amsterdam","Athens","Barcelona","Berlin","Bucharast","Budapest",
           "Copenhagen","Dublin","Glasgow","Helsinki","Istanbul","Kiev","Lisbon",
           "London","Madrid","Malta","Minsk","Moscow","Munich","Oslo","Paris",
           "Reykjavik","Rome","Sofia","Stockholm","Tallinn","Vienna","Warsaw")

pvalues <- data.frame(matrix(NA, nrow = length(cities), ncol = length(cities)))

par(mfrow=c(2, 3))

for(i in 1:length(cities)) {
  data <- read.table(paste("City",i-1, "total_iterations_40000_runs_50_randomroutes_100.txt", sep='_'))
  assign(cities[i], data)
  hist(data$V1, main=paste("Histogram of Profits Homebase", cities[i], sep=" "), xlab="Profits (€mln)")
}

for(i in 1:length(cities)) {
  city1 <- cities[i]
  for(j in 1: length(cities)) {
    if(i != j) {
      city2 <- cities[j]
      p <- wilcox.test(eval(parse(text = city1))$V1, eval(parse(text = city2))$V1, alternative="greater")$p.value
      pvalues[[i,j]] = p
    }

  }
}

alpha <- 0.01
best_profits_city <- cities[1]

for(i in 1:nrow(pvalues)) {
  for(j in 1: ncol(pvalues)) {
    if(i != j) {
      # if P-value of city1 to city2 > alpha, we do not reject H0 so distr. might be same -> check other way around
      if(pvalues[[i,j]] > alpha) {
        if(pvalues[j,i] <= alpha) { #then city2 profits > city1 profits
          best_profits_city = cities[j]
        }
      }
    }
    
  }
}
print(best_profits_city)



