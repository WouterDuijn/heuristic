plotHillClimber <- function(toBePlotted, runs, totalIterations, rroutes) {
  setwd("C:/workspace/heuristic/HillClimber")
  
  if(missing(toBePlotted)) {
    toBePlotted = 5
  } else if(toBePlotted > 5) {
    toBePlotted = 5
  }

  mycols <- c("blue", "black", "red", "green", "purple")
  
  best_profits <- c(0, 0, 0, 0, 0)
  min_best_profits <- min(best_profits)
  index_min_best <- which(best_profits == min_best_profits)[1]
  
  best_filenumbers <- c(0, 0, 0, 0, 0)
  
  plotted <- 0
  
  for(i in 1:runs) {
    file <- paste("HC_Schedule",i,"runs", runs,
                  "total_iterations", totalIterations,
                  "randomroutes", rroutes, sep='_')
    file <- paste(file, ".txt", sep='')
    
    data <- read.table(file, header=FALSE, sep="\t")
    #data$V2 = data$V2 * 1000000
    max_profit <- max(data$V2)
    
    if(max_profit > min_best_profits) {
      best_profits[index_min_best] = max_profit
      best_filenumbers[index_min_best] = i
      min_best_profits = min(best_profits)
      index_min_best = which(best_profits == min_best_profits)[1]
    }
  }
  
  for(j in 1:toBePlotted) {
    file <- paste("HC_Schedule",best_filenumbers[j],"runs", runs,
                  "total_iterations", totalIterations,
                  "randomroutes", rroutes, sep='_')
    file <- paste(file, ".txt", sep='')
    if(plotted > 0) {
      lines(read.table(file, header=FALSE, sep="\t"), col=mycols[j])
      plotted = plotted+1
      
    } else {
      plot(read.table(file, header=FALSE, sep="\t"), type='l', col=mycols[j],
           yaxt="n", xaxt = "n", xlab="", ylab="", ylim=c(7, 12.5), xlim=c(-100, 4000))
      axis(2, cex.axis=1.25)
      axis(1, cex.axis=1.5)
      mtext("State", side=1, line=2.2, cex=1.5)
      mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
      title(main=, "Hill Climber Scores", cex.main=2)
      
      plotted = plotted+1
      
    }
  }
  print(best_profits)
  print(best_filenumbers)
  
}

plotHillClimberRestart <- function(toBePlotted, runs, totalIterations, rroutes) {
  setwd("C:/workspace/heuristic/HillClimberRestart")
  
  if(missing(toBePlotted)) {
    toBePlotted = 5
  } else if(toBePlotted > 5) {
    toBePlotted = 5
  }
  
  mycols <- c("blue", "black", "red", "green", "purple")
  
  best_profits <- c(0, 0, 0, 0, 0)
  min_best_profits <- min(best_profits)
  index_min_best <- which(best_profits == min_best_profits)[1]
  
  best_filenumbers <- c(0, 0, 0, 0, 0)
  
  plotted <- 0
  
  for(i in 1:runs) {
    file <- paste("HCRestart",i,"runs", runs,
                  "total_iterations", totalIterations,
                  "randomroutes", rroutes, sep='_')
    file <- paste(file, ".txt", sep='')
    
    data <- read.table(file, header=FALSE, sep="\t")
    #data$V2 = data$V2 * 1000000
    max_profit <- max(data$V2)
    
    if(max_profit > min_best_profits) {
      best_profits[index_min_best] = max_profit
      best_filenumbers[index_min_best] = i
      min_best_profits = min(best_profits)
      index_min_best = which(best_profits == min_best_profits)[1]
    }
  }
  
  for(j in 1:toBePlotted) {
    file <- paste("HCRestart",best_filenumbers[j],"runs", runs,
                  "total_iterations", totalIterations,
                  "randomroutes", rroutes, sep='_')
    file <- paste(file, ".txt", sep='')
    if(plotted > 0) {
      lines(read.table(file, header=FALSE, sep="\t"), col=mycols[j])
      plotted = plotted+1
      
    } else {
      plot(read.table(file, header=FALSE, sep="\t"), type='l', col=mycols[j],
           yaxt="n", xaxt = "n", xlab="", ylab="")
      axis(2, cex.axis=1.25)
      axis(1, cex.axis=1.5)
      mtext("State", side=1, line=2.2, cex=1.5)
      mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
      title(main=, "Hill Climber Restart Scores", cex.main=2)
      
      plotted = plotted+1
      
    }
  }
  print(best_profits)
  print(best_filenumbers)
  
  
}

runs = 50
totalIterations = "2000000"
randomroutes = 100

plotHillClimber(1, runs, totalIterations, randomroutes)
plotHillClimberRestart(1, runs, totalIterations, randomroutes)

plotSimulatedAnnealing <- function(temperature, coolingRate, numLines, toBePlotted) {
  setwd("C:/workspace/heuristic/SimulatedAnnealing")
  
  if(missing(toBePlotted)) {
    toBePlotted = 5
  } else if(toBePlotted > 5) {
    toBePlotted = 5
  }
  
  #cl <- colors(distinct = TRUE)
  #set.seed(15887) 
  #mycols <- sample(cl, numLines)
  mycols <- c("blue", "black", "red", "green", "purple")
  
  best_profits <- c(0, 0, 0, 0, 0)
  min_best_profits <- min(best_profits)
  index_min_best <- which(best_profits == min_best_profits)[1]
  
  best_filenumbers <- c(0, 0, 0, 0, 0)
  
  plotted <- 0
  
  for(i in 1:numLines) {
    file <- paste("Schedule",i,"Temp", temperature, "CR", coolingRate, sep='_')
    file <- paste(file, ".txt", sep='')
    
    data <- read.table(file, header=FALSE, sep="\t")
    #data$V2 = data$V2 * 1000000
    max_profit <- max(data$V2)
    
    if(max_profit > min_best_profits) {
      best_profits[index_min_best] = max_profit
      best_filenumbers[index_min_best] = i
      min_best_profits = min(best_profits)
      index_min_best = which(best_profits == min_best_profits)[1]
    }
  }
  
  for(j in 1:toBePlotted) {
    file <- paste("Schedule",best_filenumbers[j],"Temp", temperature, "CR", coolingRate, sep='_')
    file <- paste(file, ".txt", sep='')
    if(plotted > 0) {
      lines(read.table(file, header=FALSE, sep="\t"), col=mycols[j])
      plotted = plotted+1
      
    } else {
      plot(read.table(file, header=FALSE, sep="\t"), type='l', col=mycols[j],
           yaxt="n", xaxt = "n", xlab="", ylab="", ylim=c(7, 12.5))
      axis(2, cex.axis=1.25)
      axis(1, cex.axis=1.5)
      mtext("State", side=1, line=2.2, cex=1.5)
      mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
      title(main=, "Simulated Annealing Scores", cex.main=2)
      
      plotted = plotted+1
      
    }
  }
  print(best_profits)
  print(best_filenumbers)
}

temp <- 163048
CR <- "3.0E-4"
plotSimulatedAnnealing(temp, CR, runs, 1)
