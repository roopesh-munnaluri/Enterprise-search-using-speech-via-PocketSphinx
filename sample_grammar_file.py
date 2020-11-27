with open('sample.dic',"r") as f:
    wordlist = [r.split()[0] for r in f]
final_wordlist = []
for word in wordlist:
    final_wordlist.append(word)
    final_wordlist.append('<s>\n')
file = open('sample_test.dic',"w")
file.writelines(final_wordlist)
