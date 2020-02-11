import argparse


def process(filename):
    with open(filename) as f:
        for line in f:
            vals = line.split('¦')
            if vals[3] == 'normalturn':
                if vals[5] != 'server':
                    spkr = vals[5]
                    k = 10
                    print(f"{spkr:>{k}}: {vals[7]}")
                else:
                    print(f"{spkr:>{k}}*: {vals[7]}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("filename", help="Path to turnsatribvals.txt")
    args = parser.parse_args()
    process(args.filename)
