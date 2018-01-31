setwd("C:/workspace/heuristic/BestHomeTown")

cities = c("Amsterdam","Athens","Barcelona","Berlin","Bucharast","Budapest",
           "Copenhagen","Dublin","Glasgow","Helsinki","Istanbul","Kiev","Lisbon",
           "London","Madrid","Malta","Minsk","Moscow","Munich","Oslo","Paris",
           "Reykjavik","Rome","Sofia","Stockholm","Tallinn","Vienna","Warsaw")

pvalues <- data.frame(matrix(NA, nrow = length(cities), ncol = length(cities)))
variances <- numeric(length(cities))

par(mfrow=c(2, 3))

for(i in 1:length(cities)) {
  data <- read.table(paste("City",i-1, "total_iterations_40000_runs_50_randomroutes_100.txt", sep='_'))
  assign(cities[i], data)
  variances[i] = var(eval(parse(text = cities[i]))$V1)
  hist(data$V1, main=paste("Histogram of Profits Homebase", cities[i], sep=" "), xlab="Profits (€mln)")
}
print(variances) #quite similar variances, thus assumptionfor wilcox.test holds.

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

alpha <- 0.03
best_profits_city <- cities[1]

for(i in 1:nrow(pvalues)) {
  # if H0 for city i is never rejected (thus larger profits) in test versus all other cities, city i is best
  if(sum(pvalues[i,]<alpha, na.rm=TRUE) == length(cities)-1) {
    best_profits_city = cities[i]
  }
  
}
print(best_profits_city)            #Reykjavik
match(best_profits_city, cities)-1  #id=21