setwd("C:/workspace/heuristic/SimulatedAnnealing")

# highest profit
temp_10000_CR_0.003_1 <- read.table("Schedule_1_Temp_10000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_10000_CR_0.003_2 <- read.table("Schedule_2_Temp_10000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_10000_CR_0.003_3 <- read.table("Schedule_3_Temp_10000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_10000_CR_0.003_4 <- read.table("Schedule_4_Temp_10000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_10000_CR_0.003_5 <- read.table("Schedule_5_Temp_10000.0_CR_0.003.txt", header=FALSE, sep="\t")

plot(temp_10000_CR_0.003_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_10000_CR_0.003_2, col='blue')
lines(temp_10000_CR_0.003_3, col='red')
lines(temp_10000_CR_0.003_4, col='pink')
lines(temp_10000_CR_0.003_5, col='green')

temp_10000_CR_0.005_1 <- read.table("Schedule_1_Temp_10000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_10000_CR_0.005_2 <- read.table("Schedule_2_Temp_10000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_10000_CR_0.005_3 <- read.table("Schedule_3_Temp_10000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_10000_CR_0.005_4 <- read.table("Schedule_4_Temp_10000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_10000_CR_0.005_5 <- read.table("Schedule_5_Temp_10000.0_CR_0.005.txt", header=FALSE, sep="\t")

plot(temp_10000_CR_0.005_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_10000_CR_0.005_2, col='blue')
lines(temp_10000_CR_0.005_3, col='red')
lines(temp_10000_CR_0.005_4, col='pink')
lines(temp_10000_CR_0.005_5, col='green')

temp_100000_CR_0.003_1 <- read.table("Schedule_1_Temp_100000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_2 <- read.table("Schedule_2_Temp_100000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_3 <- read.table("Schedule_3_Temp_100000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_4 <- read.table("Schedule_4_Temp_100000.0_CR_0.003.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_5 <- read.table("Schedule_5_Temp_100000.0_CR_0.003.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.003_1, xlab="Mutation", ylab="Profit (€)", type='l', lty=3)
lines(temp_100000_CR_0.003_2, col='blue')
lines(temp_100000_CR_0.003_3, col='red')
lines(temp_100000_CR_0.003_4, col='pink')
lines(temp_100000_CR_0.003_5, col='green')

plot(temp_100000_CR_0.003_1, type='l', lty=3, col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("Mutation", side=1, line=2.2, cex=1.5)
mtext("Profit (€)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Restart Scores", cex.main=2)

#Temperature 100,000 CR=0.001
setwd("C:/workspace/heuristic/SimulatedAnnealing")

temp_100000_CR_0.001_1 <- read.table("Schedule_1_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_2 <- read.table("Schedule_2_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_3 <- read.table("Schedule_3_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_4 <- read.table("Schedule_4_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_5 <- read.table("Schedule_5_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.001_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="", xlim=c(-100,6000))
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_100000_CR_0.001_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_0.001randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_0.001randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_0.001randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_0.001randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_0.001randomroutes_10000.txt", header=FALSE, sep="\t")

# SIMULATED ANNEALING

#NEWEST. Using 500,000 total iterations (50 runs of 10,000 iterations)
plotSimulatedAnnealing <- function(temperature, coolingRate, numLines) {
  setwd("C:/workspace/heuristic/SimulatedAnnealing")
  
  #cl <- colors(distinct = TRUE)
  #set.seed(15887) 
  #mycols <- sample(cl, numLines)
  mycols <- c("blue", "black", "red", "green", "purple")
  
  best_profits <- c(0, 0, 0, 0, 0)
  min_best_profits <- min(best)
  index_min_best <- which(best == min_best_profits)[1]
  
  best_filenumbers <- c(0, 0, 0, 0, 0)
  
  plotted <- 0
  
  for(i in 1:50) {
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
  
  for(j in 1:5) {
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

# Highest profit 11.53717 mln, schedule 1
temp <- 22136
CR <- 0.001
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 and 50

# Highest profit 11.40814 mln, schedule 6
temp = 148
CR = "5.0E-4"
plotSimulatedAnnealing(temp, CR, 50)

# Highest profit 11.38497 mln, schedule 47
temp = 22081
CR = "5.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 1,000,000 and 50

# Highest profit 11.65386 mln, schedule 38
temp = 487597909
CR = "5.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 
# Too many deteriorations accepted in early stages. 

# Highest profit 11.67473 mln, schedule 43
temp = 163048
CR ="3.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 

# Highest profit 11.51677 mln, schedule 31
temp = 8914599
CR = "4.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 
# Temperature too high

# Highest profit 11.51552 mln, schedule 4
temp = 1205554
CR = "3.5E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 
# Temperature too high

# Highest profit 11.78700 mln, schedule 43
temp = 541543
CR = "3.3E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 
# Only 1 high value, rest quite much lower

# Highest profit 11.57994 mln, schedule 44
temp = 109281
CR = "2.9E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 2,000,000 total iterations and 50 runs. 
# Cooling rate too low

# Highest profit 11.61040 mln, schedule 33
temp = 163341
CR = "6.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,000,000 total iterations and 50 runs. 

# Highest profit 11.46907 mln, schedule 18
temp = 270443
CR = "0.00125"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# Very low results

# Highest profit 11.59279 mln, schedule 44
temp = 494979401
CR = "0.002"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# Temperature too high

# Highest profit 11.54825 mln, schedule 33
temp = 60237
CR = "0.0011"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# low results, too low a temperature

# Highest profit 11.52851 mln, schedule 49
temp = 99371
CR = "0.00115"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# less low results than above, but not good, too low a temperature?

# Highest profit 11.55542 mln, schedule 26
temp = 163931
CR = "0.0012"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# very low results

# Highest profit 11.63280 mln, schedule 18
temp = 446170
CR = "0.0013"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# very low results apart from schedule 18 

# Highest profit 11.67577 mln, schedule 44
temp = 270443
CR = "0.00125"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# quite low results

# Highest profit 11.42537 mln, schedule 24
temp = 200275
CR = "0.00122"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 500,000 total iterations and 50 runs. 
# VERY low results

# !! Highest profit 11.77145 mln, schedule 45
temp = 163145
CR = "4.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,500,000 total iterations and 50 runs. 
# not very great results, but 1 high profit 

# Highest profit 11.53607 mln, schedule 26
temp = 220250
CR = "4.1E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,500,000 total iterations and 50 runs. 
# low results, much too low a temperature 

# Highest profit 11.55660 mln, schedule 26
temp = 140412
CR = "3.95E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,500,000 total iterations and 50 runs. 
# CR too low? 

# Highest profit 11.77853 mln, schedule 26
temp = 158322
CR = "3.99E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,500,000 total iterations and 50 runs. 
# low results except for one, CR too small

# Highest profit 11.55454 mln, schedule 44
temp = 255910
CR = "4.15E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,500,000 total iterations and 50 runs. 
# low results except for one, CR too small

# Highest profit 11.30982 mln, schedule 14
temp = 4030
CR = "4.15E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,000,000 total iterations and 50 runs. 
# extremely low results, temperature FAR too small.

# !!!Highest profit 11.99850 mln, schedule 23
temp = 109465
CR = "5.8E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,000,000 total iterations and 50 runs. 
# 1 great result, 2 quite alright, temperature a little too small

# Highest profit 11.50345 mln, schedule 36
temp = 133717
CR = "5.9E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,000,000 total iterations and 50 runs. 
# low results, temperature, CR a little too small

# Highest profit 11.34097 mln, schedule 14
temp = 199530
CR = "6.1E-4"
plotSimulatedAnnealing(temp, CR, 50)
# tested for 1,000,000 total iterations and 50 runs. 
# low results

# Highest profit 11.55062 mln, schedule 50
temp = 199530
CR = "0.001"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# low results

# Highest profit 11.34447 mln, schedule 46
temp = 199530
CR = "0.003"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# low results and weird graph

# Highest profit 11.53667 mln, schedule 37
temp = 199530
CR = "0.002"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# okay results

# Highest profit 11.60405 mln, schedule 20
temp = 199530
CR = "0.0015"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# only 1 better result

# Highest profit 11.38967 mln, schedule 44
temp = 199530
CR = "0.004"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# Extremely low results. Too high CR

# Highest profit 11.62747 mln, schedule 11
temp = 199530
CR = "0.0025"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# low results, only 1 good

# Highest profit 11.49981 mln, schedule 35
temp = 199530
CR = "0.0023"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# Overall better results than the one above, but not in maximum

# Highest profit 11.71672 mln, schedule 1
temp = "100000"
CR = "1.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# Overall much better results than the one above

# Highest profit 11.45378 mln, schedule 1
temp = "100000"
CR = "5.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# okay results

# Highest profit 11.48190 mln, schedule 32
temp = "100000"
CR = "3.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# okay results, best 5 all around 11.4 - 11.45

# Highest profit 11.54830 mln, schedule 44
temp = "150000"
CR = "0.001"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# okay results

# Highest profit 11.47305 mln, schedule 41
temp = "150000"
CR = "0.002"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# less okay results (around 11.33-11.35)

# Highest profit 11.60844 mln, schedule 34
temp = "150000"
CR = "5.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# 3 quite good results (11.6, 11.54-11.56), rest 11.35

# Highest profit 11.40621 mln, schedule 30
temp = "150000"
CR = "8.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# Results around 11.3-11.4

# Highest profit 11.39080 mln, schedule 49
temp = "20"
CR = "3.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# extremely low results

# Highest profit 11.42400 mln, schedule 40
temp = "404"
CR = "6.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# very low results

# Highest profit 11.32636 mln, schedule 39
temp = "8135"
CR = "9.0E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# very low results

# Highest profit 11.41072 mln, schedule 21
temp = "13420"
CR = "9.5E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.2 - 11.37 besides highest

# Highest profit 11.57121 mln, schedule 24
temp = "16394"
CR = "9.7E-4"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.2 - 11.4 besides highest

# Highest profit 11.56771 mln, schedule 33
temp = "148313"
CR = "0.00119"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.3 - 11.42 besides highest

# Highest profit 11.53824 mln, schedule 14
temp = "162298"
CR = "0.001199"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.3 - 11.54 besides highest

# Highest profit 11.65706 mln, schedule 17
temp = "181194"
CR = "0.00121"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.37 - 11.49 besides highest

# Highest profit 11.44778 mln, schedule 15
temp = "200275"
CR = "0.00122"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.22 - 11.26 besides highest

# Highest profit 11.43591 mln, schedule 15
temp = "183017"
CR = "0.001211"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.27 - 11.32 besides highest

# Highest profit 11.54967 mln, schedule 15
temp = "221365"
CR = "0.00123"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# Results between 11.29 - 11.38 besides highest

# Highest profit 11.37146 mln, schedule 15
temp = "210556"
CR = "0.001225"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs 
# very low results (11.1)








#Linear multiplicative temperature function

# Highest profit 11.40621 mln, schedule 30
temp = "100000"
CR = "5.0"
plotSimulatedAnnealing(temp, CR, 50)
# - 
# Results around 11.3-11.4

# Highest profit 11.49865 mln, schedule 47
temp = "100000"
CR = "2.0"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs
# Results around 11.3-11.4

# Highest profit 11.35673 mln, schedule 33
temp = "100000"
CR = "9.0"
plotSimulatedAnnealing(temp, CR, 50)
# 500,000 total iterations, 50 runs
# Results around 11.3-11.4























#OLD

plot(temp_100000_CR_0.001_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_100000_CR_0.003_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_0.003_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_0.003_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_0.003_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_0.003_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.003_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_0.003_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.003_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_10000_CR_0.001_rr10000_1 <- read.table("Schedule_1_Temp_10000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_10000_CR_0.001_rr10000_2 <- read.table("Schedule_2_Temp_10000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_10000_CR_0.001_rr10000_3 <- read.table("Schedule_3_Temp_10000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_10000_CR_0.001_rr10000_4 <- read.table("Schedule_4_Temp_10000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_10000_CR_0.001_rr10000_5 <- read.table("Schedule_5_Temp_10000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_10000_CR_0.001_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_50000_CR_0.001_rr10000_1 <- read.table("Schedule_1_Temp_50000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_50000_CR_0.001_rr10000_2 <- read.table("Schedule_2_Temp_50000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_50000_CR_0.001_rr10000_3 <- read.table("Schedule_3_Temp_50000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_50000_CR_0.001_rr10000_4 <- read.table("Schedule_4_Temp_50000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_50000_CR_0.001_rr10000_5 <- read.table("Schedule_5_Temp_50000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_50000_CR_0.001_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_75000_CR_0.001_rr10000_1 <- read.table("Schedule_1_Temp_75000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_75000_CR_0.001_rr10000_2 <- read.table("Schedule_2_Temp_75000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_75000_CR_0.001_rr10000_3 <- read.table("Schedule_3_Temp_75000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_75000_CR_0.001_rr10000_4 <- read.table("Schedule_4_Temp_75000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_75000_CR_0.001_rr10000_5 <- read.table("Schedule_5_Temp_75000.0_CR_0.001_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_75000_CR_0.001_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_100000_CR_0.00075_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.00075_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

#Profit of 12 mil.
temp_100000_CR_0.00075_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00075_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_7.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.00075_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_100000_CR_0.0005_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_5.0E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.0005_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_5.0E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.0005_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_5.0E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.0005_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_5.0E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.0005_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_5.0E-4_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.0005_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_100000_CR_0.00065_rr10000_1 <- read.table("Schedule_1_Temp_100000.0_CR_6.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00065_rr10000_2 <- read.table("Schedule_2_Temp_100000.0_CR_6.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00065_rr10000_3 <- read.table("Schedule_3_Temp_100000.0_CR_6.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00065_rr10000_4 <- read.table("Schedule_4_Temp_100000.0_CR_6.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")
temp_100000_CR_0.00065_rr10000_5 <- read.table("Schedule_5_Temp_100000.0_CR_6.5E-4_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.00065_rr10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

#Temperature 1,000,000, CR = 0.001
temp_1000000_CR_0.001_1 <- read.table("Schedule_1_Temp_1000000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.001_2 <- read.table("Schedule_2_Temp_1000000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.001_3 <- read.table("Schedule_3_Temp_1000000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.001_4 <- read.table("Schedule_4_Temp_1000000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.001_5 <- read.table("Schedule_5_Temp_1000000.0_CR_0.001.txt", header=FALSE, sep="\t")

plot(temp_1000000_CR_0.001_1, xlab="Mutation", ylab="Profit (€)", type='l', lty=3)
lines(temp_1000000_CR_0.001_2, col='blue')
lines(temp_1000000_CR_0.001_3, col='red')
lines(temp_1000000_CR_0.001_4, col='pink')
lines(temp_1000000_CR_0.001_5, col='green')

plot(temp_1000000_CR_0.001_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("Mutations", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Simulated Annealing Scores", cex.main=2)

temp_300000_CR_0.005_1 <- read.table("Schedule_1_Temp_300000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_300000_CR_0.005_2 <- read.table("Schedule_2_Temp_300000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_300000_CR_0.005_3 <- read.table("Schedule_3_Temp_300000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_300000_CR_0.005_4 <- read.table("Schedule_4_Temp_300000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_300000_CR_0.005_5 <- read.table("Schedule_5_Temp_300000.0_CR_0.005.txt", header=FALSE, sep="\t")

plot(temp_300000_CR_0.005_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_300000_CR_0.005_2, col='blue')
lines(temp_300000_CR_0.005_3, col='red')
lines(temp_300000_CR_0.005_4, col='pink')
lines(temp_300000_CR_0.005_5, col='green')

temp_1000000_CR_0.005_1 <- read.table("Schedule_1_Temp_1000000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.005_2 <- read.table("Schedule_2_Temp_1000000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.005_3 <- read.table("Schedule_3_Temp_1000000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.005_4 <- read.table("Schedule_4_Temp_1000000.0_CR_0.005.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.005_5 <- read.table("Schedule_5_Temp_1000000.0_CR_0.005.txt", header=FALSE, sep="\t")

plot(temp_1000000_CR_0.005_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_1000000_CR_0.005_2, col='blue')
lines(temp_1000000_CR_0.005_3, col='red')
lines(temp_1000000_CR_0.005_4, col='pink')
lines(temp_1000000_CR_0.005_5, col='green')

#adjusting cooling rate
temp_10000_CR_0.01_1 <- read.table("Schedule_1_Temp_10000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_10000_CR_0.01_2 <- read.table("Schedule_2_Temp_10000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_10000_CR_0.01_3 <- read.table("Schedule_3_Temp_10000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_10000_CR_0.01_4 <- read.table("Schedule_4_Temp_10000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_10000_CR_0.01_5 <- read.table("Schedule_5_Temp_10000.0_CR_0.01.txt", header=FALSE, sep="\t")

plot(temp_10000_CR_0.01_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_10000_CR_0.01_2, col='blue')
lines(temp_10000_CR_0.01_3, col='red')
lines(temp_10000_CR_0.01_4, col='pink')
lines(temp_10000_CR_0.01_5, col='green')

temp_1000000_CR_0.01_1 <- read.table("Schedule_1_Temp_1000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.01_2 <- read.table("Schedule_2_Temp_1000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.01_3 <- read.table("Schedule_3_Temp_1000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.01_4 <- read.table("Schedule_4_Temp_1000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000000_CR_0.01_5 <- read.table("Schedule_5_Temp_1000000.0_CR_0.01.txt", header=FALSE, sep="\t")

plot(temp_1000000_CR_0.01_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_1000000_CR_0.01_2, col='blue')
lines(temp_1000000_CR_0.01_3, col='red')
lines(temp_1000000_CR_0.01_4, col='pink')
lines(temp_1000000_CR_0.01_5, col='green')

temp_1000_CR_0.01_1 <- read.table("Schedule_1_Temp_1000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000_CR_0.01_2 <- read.table("Schedule_2_Temp_1000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000_CR_0.01_3 <- read.table("Schedule_3_Temp_1000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000_CR_0.01_4 <- read.table("Schedule_4_Temp_1000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_1000_CR_0.01_5 <- read.table("Schedule_5_Temp_1000.0_CR_0.01.txt", header=FALSE, sep="\t")

plot(temp_1000_CR_0.01_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_1000_CR_0.01_2, col='blue')
lines(temp_1000_CR_0.01_3, col='red')
lines(temp_1000_CR_0.01_4, col='pink')
lines(temp_1000_CR_0.01_5, col='green')

temp_3000000_CR_0.01_1 <- read.table("Schedule_1_Temp_3000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_3000000_CR_0.01_2 <- read.table("Schedule_2_Temp_3000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_3000000_CR_0.01_3 <- read.table("Schedule_3_Temp_3000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_3000000_CR_0.01_4 <- read.table("Schedule_4_Temp_3000000.0_CR_0.01.txt", header=FALSE, sep="\t")
temp_3000000_CR_0.01_5 <- read.table("Schedule_5_Temp_3000000.0_CR_0.01.txt", header=FALSE, sep="\t")

plot(temp_3000000_CR_0.01_1, xlab="Mutation", ylab="Profit (€)", type='l')
lines(temp_3000000_CR_0.01_2, col='blue')
lines(temp_3000000_CR_0.01_3, col='red')
lines(temp_3000000_CR_0.01_4, col='pink')
lines(temp_3000000_CR_0.01_5, col='green')

#Hill climber restart
setwd("C:/workspace/heuristic/HillClimberRestart")

#NO_IMPROVEMENT_ITERATIONS = 10,000
init_20_rr_1000_1 <- read.table("HCRestart_Schedule_1_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_1000_2 <- read.table("HCRestart_Schedule_2_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_1000_3 <- read.table("HCRestart_Schedule_3_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_1000_4 <- read.table("HCRestart_Schedule_4_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_1000_5 <- read.table("HCRestart_Schedule_5_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")

#plot(init_20_rr_1000_1, xlab="Mutation", ylab="Profit (€)", type='l', lty=3, main="Hill Climber Restart Scores", col="blue",
#     yaxt="n", xaxt = "n")
plot(init_20_rr_1000_1, type='l', lty=3, col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("Mutation", side=1, line=2.2, cex=1.5)
mtext("Profit (€)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Restart Scores", cex.main=2)

lines(init_20_rr_1000_2, col='black')
lines(init_20_rr_1000_3, col='red')
lines(init_20_rr_1000_4, col='pink')
lines(init_20_rr_1000_5, col='green')

# NO_IMPROVEMENT_ITERATIONS = 1,000
init_20_rr_10000_1 <- read.table("HCRestart_Schedule_1_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_2 <- read.table("HCRestart_Schedule_2_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_3 <- read.table("HCRestart_Schedule_3_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_4 <- read.table("HCRestart_Schedule_4_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_5 <- read.table("HCRestart_Schedule_5_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")

init_20_rr_10000_6 <- read.table("HCRestart_Schedule_6_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_7 <- read.table("HCRestart_Schedule_7_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_8 <- read.table("HCRestart_Schedule_8_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_9 <- read.table("HCRestart_Schedule_9_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_10 <- read.table("HCRestart_Schedule_10_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")

init_20_rr_10000_11 <- read.table("HCRestart_Schedule_11_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_12 <- read.table("HCRestart_Schedule_12_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_13 <- read.table("HCRestart_Schedule_13_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_14 <- read.table("HCRestart_Schedule_14_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_15 <- read.table("HCRestart_Schedule_15_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")

init_20_rr_10000_16 <- read.table("HCRestart_Schedule_16_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_17 <- read.table("HCRestart_Schedule_17_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_18 <- read.table("HCRestart_Schedule_18_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_19 <- read.table("HCRestart_Schedule_19_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_20 <- read.table("HCRestart_Schedule_20_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")

max(init_20_rr_10000_1$V2, init_20_rr_10000_2$V2,init_20_rr_10000_3$V2,init_20_rr_10000_4$V2,init_20_rr_10000_5$V2,
    init_20_rr_10000_6$V2,init_20_rr_10000_7$V2,init_20_rr_10000_8$V2,init_20_rr_10000_9$V2,init_20_rr_10000_10$V2,
    init_20_rr_10000_11$V2,init_20_rr_10000_12$V2,init_20_rr_10000_13$V2,init_20_rr_10000_14$V2,init_20_rr_10000_15$V2,
    init_20_rr_10000_16$V2,init_20_rr_10000_17$V2,init_20_rr_10000_18$V2,init_20_rr_10000_19$V2,init_20_rr_10000_20$V2)

setwd("C:/workspace/heuristic/HillClimberRestart")

hillclimberrestart <- read.table("HCRestart__initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")


plot(hillclimberrestart, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Restart Scores", cex.main=2)
#plot(init_20_rr_10000_1, xlab="Mutation", ylab="Profit (€)", type='l', lty=3, main="Hill climber restart scores")
#lines(init_20_rr_10000_2, col='black', lty=3)
#lines(init_20_rr_10000_1, col='red', lty=3)
#lines(init_20_rr_10000_4, col='pink', lty=3)
#lines(init_20_rr_10000_5, col='green', lty=3)

# NO_IMPROVEMENT_ITERATIONS = 100
init_20_rr_10000_1.2 <- read.table("HCRestart_Schedule_1_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_2.2 <- read.table("HCRestart_Schedule_2_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_3.2 <- read.table("HCRestart_Schedule_3_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_4.2 <- read.table("HCRestart_Schedule_4_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")
init_20_rr_10000_5.2 <- read.table("HCRestart_Schedule_5_initialschedules_20_randomroutes_10000.txt", header=FALSE, sep="\t")

plot(init_20_rr_10000_1.2, type='l', lty=3, col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("Mutation", side=1, line=2.2, cex=1.5)
mtext("Profit (€)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Restart Scores", cex.main=2)
#plot(init_20_rr_10000_1.2, xlab="Mutation", ylab="Profit (€)", type='l', lty=3, main="Hill climber restart scores")
lines(init_20_rr_10000_2.2, col='black', lty=3)
lines(init_20_rr_10000_3.2, col='red', lty=3)
lines(init_20_rr_10000_4.2, col='pink', lty=3)
lines(init_20_rr_10000_5.2, col='green', lty=3)

plot(init_20_rr_10000_3.2, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="")
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.5)
mtext("Mutation", side=1, line=2.2, cex=1.5)
mtext("Profit (€)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Restart Scores", cex.main=2)
#plot(init_20_rr_10000_1.2, xlab="Mutation", ylab="Profit (€)", type='l', lty=3, main="Hill climber restart scores")
lines(init_20_rr_10000_2.2, col='black')
lines(init_20_rr_10000_1.2, col='red')
lines(init_20_rr_10000_4.2, col='pink')
lines(init_20_rr_10000_5.2, col='green')

#HillClimber
setwd("C:/workspace/heuristic/HillClimber")

init_20_rr_10000_1 <- read.table("HC_Schedule1_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_10000_2 <- read.table("HC_Schedule2_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_10000_3 <- read.table("HC_Schedule3_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_10000_4 <- read.table("HC_Schedule4_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")
init_20_rr_10000_5 <- read.table("HC_Schedule5_initialschedules_20_randomroutes_1000.txt", header=FALSE, sep="\t")

plot(init_20_rr_10000_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="", ylim=c(7.5,12))
axis(2, cex.axis=1.25)
axis(1, cex.axis=1.25)
mtext("State", side=1, line=2.2, cex=1.5)
mtext("Profit (€mln)", side=2, line=2.2, cex=1.5)
title(main=, "Hill Climber Scores", cex.main=2)
#plot(init_20_rr_10000_1.2, xlab="Mutation", ylab="Profit (€)", type='l', lty=3, main="Hill climber restart scores")
lines(init_20_rr_10000_2, col='black')
lines(init_20_rr_10000_3, col='red')
lines(init_20_rr_10000_4, col='purple')
lines(init_20_rr_10000_5, col='green')
