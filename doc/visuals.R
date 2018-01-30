setwd("C:/workspace/heuristic/doc")
source("data.r")
par(mfrow=c(2,2))

#Total time: 2897 seconds = 57.9
hist(random, main = "Histogram of Random Algorithm Profit", 
     breaks = 15,xlim = c(7.8,9.2), xlab="Profit (Millions)")
mean(random)
max(random)

#Total time: 333 seconds = 6.66
hist(hillclimber, main = "Histogram of HillClimber Algorithm Profit", breaks = 15,
     xlim = c(9.5,12), xlab="Profit (Millions)")
mean(hillclimber)
max(hillclimber)

#Total time: 1109 seconds = 22.18
hist(hillclimberrestart, main = "Histogram of HillClimber Restart Algorithm Profit", 
     breaks = 15,xlim = c(10.4,11.8), xlab="Profit (Millions)")
mean(hillclimberrestart)
max(hillclimberrestart)

#Total time: 369 seconds = 7.38
hist(simulated, main = "Histogram of Simulated Annealing Algorithm Profit", 
     breaks = 15,xlim = c(10.4,11.8), xlab="Profit (Millions)")
mean(simulated)
max(simulated)

1109/50

