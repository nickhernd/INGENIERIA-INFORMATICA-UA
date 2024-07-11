.data
	A: .word 8
	B: .word 6
	C: .word 3
	D: .word 0,0,0,0
.code
	Ld r1, A(r0)
	ld r2, B(r0)
	ld r3, C(r0)
	xor r5,r5,r5
	dadd r6,r2,r3
	dadd r7,r6,r3
	dadd r8,r7,r2
	sd r6,D(r5)
	dadd r5,r5,r1
	sd r7,D(r5)
	dadd r5,r5,r1
	sd r8,D(r5)
	daddi r9,r5,8
	ld r10,D(r5)
	sd r10,D(r9)
halt