setwd("C:/workspace/heuristic/doc")
source("data.r")
par(mfrow=c(2,2))

#Total time: 2897 seconds = 57.9
hist(random, main="", breaks = 15,
     xlim = c(7.8,9.2), xlab="", ylab="", yaxt="n", xaxt="n")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("Frequency", side=2, line=2.2, cex=1.2)
mtext("Profit (€mln)", side=1, line=2.2, cex=1.2)
title(main=, "Histogram Random Algorithm Profit", cex.main=1.5)
mean(random)
max(random)

#Total time: 333 seconds = 6.66
hist(hillclimber, main = "", breaks = 15,
     xlim = c(9.5,12), xlab="", ylab="", yaxt="n", xaxt="n")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("Frequency", side=2, line=2.2, cex=1.2)
mtext("Profit (€mln)", side=1, line=2.2, cex=1.2)
title(main=, "Histogram Hill Climber Profit", cex.main=1.5)
mean(hillclimber)
max(hillclimber)

#Total time: 1109 seconds = 22.18
hist(hillclimberrestart, main = "", 
     breaks = 15,xlim = c(10.4,11.8), xlab="", ylab="", yaxt="n", xaxt="n")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("Frequency", side=2, line=2.2, cex=1.2)
mtext("Profit (€mln)", side=1, line=2.2, cex=1.2)
title(main=, "Histogram Hill Climber Restart Profit", cex.main=1.5)
mean(hillclimberrestart)
max(hillclimberrestart)

#Total time: 369 seconds = 7.38
hist(simulated, main = "", 
     breaks = 15,xlim = c(10.4,11.8), xlab="", ylab="", yaxt="n", xaxt="n")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("Frequency", side=2, line=2.2, cex=1.2)
mtext("Profit (€mln)", side=1, line=2.2, cex=1.2)
title(main=, "Histogram Simulated Annealing Profit", cex.main=1.5)
mean(simulated)
max(simulated)

1109/50

