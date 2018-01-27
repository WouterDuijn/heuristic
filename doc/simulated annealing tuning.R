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
temp_100000_CR_0.001_1 <- read.table("Schedule_1_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_2 <- read.table("Schedule_2_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_3 <- read.table("Schedule_3_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_4 <- read.table("Schedule_4_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")
temp_100000_CR_0.001_5 <- read.table("Schedule_5_Temp_100000.0_CR_0.001.txt", header=FALSE, sep="\t")

plot(temp_100000_CR_0.001_1, xlab="Mutation", ylab="Profit (€)", type='l', lty=3)
lines(temp_100000_CR_0.001_2, col='blue')
lines(temp_100000_CR_0.001_3, col='red')
lines(temp_100000_CR_0.001_4, col='pink')
lines(temp_100000_CR_0.001_5, col='green')

plot(temp_100000_CR_0.001_1, type='l', col="blue",
     yaxt="n", xaxt = "n", xlab="", ylab="", xlim=c(-100,6000))
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
