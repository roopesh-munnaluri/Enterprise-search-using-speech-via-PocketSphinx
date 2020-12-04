with open('sample.dic',"r") as f:
    wordlist = [r.split()[0] for r in f]
final_wordlist = []
for word in wordlist:
    word = '<s> ' + word + ' </s>\n'
    final_wordlist.append(word)
final_wordlist = list(dict.fromkeys(final_wordlist))
print(len(wordlist))
print(len(final_wordlist))
file = open('sample_test.txt',"w")
file.writelines(final_wordlist)
